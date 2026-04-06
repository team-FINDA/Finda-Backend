package finda.findabatch.infra.event.consumer.volunteer

import com.fasterxml.jackson.databind.ObjectMapper
import finda.findabatch.global.error.exception.RemindTimeNotFoundException
import finda.findabatch.infra.event.dto.volunteer.CdcEvent
import finda.findabatch.infra.event.dto.volunteer.RecurrenceMonthSnapshot
import finda.findabatch.infra.event.dto.volunteer.RecurrenceWeekSnapshot
import finda.findabatch.infra.event.dto.volunteer.VolunteerScheduleSnapshot
import finda.findabatch.infra.event.dto.volunteer.VolunteerSnapshot
import finda.findabatch.infra.event.dto.volunteer.toLocalTime
import finda.findabatch.infra.schedule.job.volunteer.VolunteerRemindJobScheduler
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalTime
import java.util.Optional
import java.util.UUID

@Component
class VolunteerCdcEventConsumer(
    private val volunteerRemindJobScheduler: VolunteerRemindJobScheduler,
    private val objectMapper: ObjectMapper
) {
    private val log = LoggerFactory.getLogger(javaClass)
    private val remindTimeCache = volunteerRemindJobScheduler.remindTimeCache

    private inline fun <reified T> parseCdcEvent(payload: com.fasterxml.jackson.databind.JsonNode): CdcEvent<T> {
        val type = objectMapper.typeFactory.constructParametricType(CdcEvent::class.java, T::class.java)
        return objectMapper.readValue(objectMapper.treeAsTokens(payload), type)
    }

    private fun resolveRemindTime(volunteerId: UUID): LocalTime? {
        val cached = remindTimeCache[volunteerId]
            ?: throw RemindTimeNotFoundException(volunteerId)
        return cached.orElse(null)
    }

    @KafkaListener(
        topics = ["volunteer.finda.tbl_volunteer"],
        containerFactory = "cdcKafkaListenerContainerFactory"
    )
    fun consumeVolunteer(message: String?, acknowledgment: Acknowledgment) {
        if (message.isNullOrBlank()) { acknowledgment.acknowledge(); return }
        val payload = objectMapper.readTree(message)["payload"]
            ?: run { acknowledgment.acknowledge(); return }
        val event = parseCdcEvent<VolunteerSnapshot>(payload)
        when (event.op) {
            "c" -> event.after?.let {
                // remindTime 미설정이면 Optional.empty()로 명시적 캐싱
                remindTimeCache[it.id] = Optional.ofNullable(it.remindTime?.toLocalTime())
                log.info("remind_time cached: ${it.id}, value: ${it.remindTime}")
            }
            "u" -> {
                val before = event.before
                val after = event.after
                if (after != null) {
                    if (before?.remindTime != after.remindTime) {
                        if (after.remindTime == null) {
                            // 미설정으로 전환 → Optional.empty()로 캐시 갱신 후 job 삭제
                            remindTimeCache[after.id] = Optional.empty()
                            volunteerRemindJobScheduler.delete(after.id)
                            log.info("remindTime set to null, updated cache and deleted all jobs: ${after.id}")
                        } else {
                            val newRemindTime = after.remindTime.toLocalTime()
                            remindTimeCache[after.id] = Optional.of(newRemindTime)
                            volunteerRemindJobScheduler.rescheduleAll(after.id, newRemindTime)
                            log.info("remindTime changed, rescheduled all jobs: ${after.id}")
                        }
                    } else {
                        remindTimeCache[after.id] = Optional.ofNullable(after.remindTime?.toLocalTime())
                    }
                }
            }
            "d" -> event.before?.let {
                remindTimeCache.remove(it.id)
                volunteerRemindJobScheduler.delete(it.id)
                log.info("volunteer deleted, removed all jobs and cache: ${it.id}")
            }
        }
        acknowledgment.acknowledge()
    }

    @KafkaListener(
        topics = ["volunteer.finda.tbl_volunteer_schedule"],
        containerFactory = "cdcKafkaListenerContainerFactory"
    )
    fun consumeVolunteerSchedule(message: String?, acknowledgment: Acknowledgment) {
        if (message.isNullOrBlank()) { acknowledgment.acknowledge(); return }
        val payload = objectMapper.readTree(message)["payload"]
            ?: run { acknowledgment.acknowledge(); return }
        val event = parseCdcEvent<VolunteerScheduleSnapshot>(payload)
        when (event.op) {
            "c" -> event.after?.let {
                val date = LocalDate.parse(it.date)
                if (date.isBefore(LocalDate.now())) {
                    acknowledgment.acknowledge()
                    return
                }
                // null → remindTime 미설정 정상 상태, skip
                val remindTime = resolveRemindTime(it.volunteerId) ?: run {
                    log.info("remindTime not set for volunteer: ${it.volunteerId}, skipping schedule")
                    acknowledgment.acknowledge()
                    return
                }
                volunteerRemindJobScheduler.scheduleOne(it.volunteerId, date, remindTime)
                log.info("job scheduled: ${it.volunteerId}, date: $date")
            }
            "u" -> {
                val afterVolunteerId = event.after?.volunteerId
                    ?: run {
                        log.warn("'u' event has null after or volunteerId, skipping")
                        acknowledgment.acknowledge()
                        return
                    }
                event.before?.let { before ->
                    val oldDate = LocalDate.parse(before.date)
                    volunteerRemindJobScheduler.delete(before.volunteerId, oldDate)
                    log.info("old job deleted: ${before.volunteerId}, date: $oldDate")
                }
                event.after?.let { after ->
                    val newDate = LocalDate.parse(after.date)
                    if (newDate.isBefore(LocalDate.now())) {
                        acknowledgment.acknowledge()
                        return
                    }
                    // null → remindTime 미설정 정상 상태, skip
                    val remindTime = resolveRemindTime(afterVolunteerId) ?: run {
                        log.info("remindTime not set for volunteer: $afterVolunteerId, skipping schedule")
                        acknowledgment.acknowledge()
                        return
                    }
                    volunteerRemindJobScheduler.scheduleOne(after.volunteerId, newDate, remindTime)
                    log.info("job rescheduled: ${after.volunteerId}, date: $newDate")
                }
            }
            "d" -> event.before?.let {
                val date = LocalDate.parse(it.date)
                volunteerRemindJobScheduler.delete(it.volunteerId, date)
                log.info("job deleted: ${it.volunteerId}, date: $date")
            }
        }
        acknowledgment.acknowledge()
    }

    @KafkaListener(
        topics = ["volunteer.finda.activity_recurrence_week"],
        containerFactory = "cdcKafkaListenerContainerFactory"
    )
    fun consumeRecurrenceWeek(message: String?, acknowledgment: Acknowledgment) {
        if (message.isNullOrBlank()) { acknowledgment.acknowledge(); return }
        val payload = objectMapper.readTree(message)["payload"]
            ?: run { acknowledgment.acknowledge(); return }
        val event = parseCdcEvent<RecurrenceWeekSnapshot>(payload)
        when (event.op) {
            "c", "u", "d" -> {
                val volunteerId = (event.after ?: event.before)?.volunteerId
                    ?: run { acknowledgment.acknowledge(); return }
                volunteerRemindJobScheduler.delete(volunteerId)
                log.info("recurrence_week changed, deleted all jobs: $volunteerId")
            }
            else -> log.debug("ignored recurrence_week event: op={}", event.op)
        }
        acknowledgment.acknowledge()
    }

    @KafkaListener(
        topics = ["volunteer.finda.activity_recurrence_month"],
        containerFactory = "cdcKafkaListenerContainerFactory"
    )
    fun consumeRecurrenceMonth(message: String?, acknowledgment: Acknowledgment) {
        if (message.isNullOrBlank()) { acknowledgment.acknowledge(); return }
        val payload = objectMapper.readTree(message)["payload"]
            ?: run { acknowledgment.acknowledge(); return }
        val event = parseCdcEvent<RecurrenceMonthSnapshot>(payload)
        when (event.op) {
            "c", "u", "d" -> {
                val volunteerId = (event.after ?: event.before)?.volunteerId
                    ?: run { acknowledgment.acknowledge(); return }
                volunteerRemindJobScheduler.delete(volunteerId)
                log.info("recurrence_month changed, deleted all jobs: $volunteerId")
            }
            else -> log.debug("ignored recurrence_month event: op={}", event.op)
        }
        acknowledgment.acknowledge()
    }
}
