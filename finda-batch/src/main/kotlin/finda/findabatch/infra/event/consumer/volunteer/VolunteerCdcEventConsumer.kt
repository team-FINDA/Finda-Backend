package finda.findabatch.infra.event.consumer.volunteer

import com.fasterxml.jackson.databind.ObjectMapper
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
                it.remindTime?.let { time ->
                    remindTimeCache[it.id] = time.toLocalTime()
                    log.info("remind_time cached: ${it.id}")
                }
            }
            "u" -> {
                val before = event.before
                val after = event.after
                if (after != null) {
                    after.remindTime?.let { time ->
                        remindTimeCache[after.id] = time.toLocalTime()
                    }
                    if (before?.remindTime != after.remindTime) {
                        volunteerRemindJobScheduler.delete(after.id)
                        log.info("remind_time changed, deleted all jobs: ${after.id}")
                    }
                }
            }
            "d" -> event.before?.let {
                volunteerRemindJobScheduler.delete(it.id)
                remindTimeCache.remove(it.id)
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
                val remindTime = remindTimeCache[it.volunteerId]
                if (remindTime == null) {
                    log.warn("remind_time not cached for volunteer: ${it.volunteerId}, skipping")
                    acknowledgment.acknowledge()
                    return
                }
                val date = LocalDate.parse(it.date)
                if (date.isBefore(LocalDate.now())) {
                    acknowledgment.acknowledge()
                    return
                }
                volunteerRemindJobScheduler.scheduleOne(it.volunteerId, date, remindTime)
                log.info("job scheduled: ${it.volunteerId}, date: $date")
            }
            "u" -> {
                val remindTime = remindTimeCache[event.after?.volunteerId]
                if (remindTime == null) {
                    log.warn("remind_time not cached for volunteer: ${event.after?.volunteerId}, skipping")
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
        val volunteerId = (event.after ?: event.before)?.volunteerId
            ?: run { acknowledgment.acknowledge(); return }
        volunteerRemindJobScheduler.delete(volunteerId)
        log.info("recurrence_week changed, deleted all jobs: $volunteerId")
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
        val volunteerId = (event.after ?: event.before)?.volunteerId
            ?: run { acknowledgment.acknowledge(); return }
        volunteerRemindJobScheduler.delete(volunteerId)
        log.info("recurrence_month changed, deleted all jobs: $volunteerId")
        acknowledgment.acknowledge()
    }
}
