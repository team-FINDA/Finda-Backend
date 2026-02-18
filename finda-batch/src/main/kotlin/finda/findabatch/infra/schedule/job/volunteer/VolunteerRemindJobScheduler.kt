package finda.findabatch.infra.schedule.job.volunteer

import finda.findabatch.infra.event.dto.volunteer.VolunteerRemindEvent
import org.quartz.JobBuilder
import org.quartz.JobKey
import org.quartz.Scheduler
import org.quartz.SimpleScheduleBuilder
import org.quartz.TriggerBuilder
import org.quartz.TriggerKey
import org.quartz.impl.matchers.GroupMatcher
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.Date
import java.util.UUID

@Component
class VolunteerRemindJobScheduler(
    private val scheduler: Scheduler
) {
    fun schedule(event: VolunteerRemindEvent) {
        event.scheduleDate.forEach { date ->
            schedule(event.volunteerId, date, event.remindTime)
        }
    }

    private fun schedule(volunteerId: UUID, scheduleDate: LocalDate, remindTime: LocalTime) {
        val identity = "${volunteerId}_$scheduleDate"
        val jobKey = JobKey.jobKey(identity, "volunteer-remind")
        val triggerKey = TriggerKey.triggerKey(identity, "volunteer-remind")

        val jobDetail = JobBuilder.newJob(VolunteerRemindJob::class.java)
            .withIdentity(jobKey)
            .usingJobData("volunteerId", volunteerId.toString())
            .build()

        val triggerTime = LocalDateTime.of(scheduleDate, remindTime)
            .atZone(ZoneId.of("Asia/Seoul"))
            .toInstant()

        val trigger = TriggerBuilder.newTrigger()
            .withIdentity(triggerKey)
            .forJob(jobKey)
            .startAt(Date.from(triggerTime))
            .withSchedule(SimpleScheduleBuilder.simpleSchedule())
            .build()

        when {
            scheduler.checkExists(triggerKey) -> scheduler.rescheduleJob(triggerKey, trigger)
            scheduler.checkExists(jobKey) -> scheduler.scheduleJob(trigger)
            else -> scheduler.scheduleJob(jobDetail, trigger)
        }
    }

    // 해당 봉사 job 전부 삭제
    fun delete(volunteerId: UUID) {
        val prefix = "${volunteerId}_"
        val jobKeys = scheduler.getJobKeys(GroupMatcher.jobGroupEquals("volunteer-remind"))
        jobKeys
            .filter { it.name.startsWith(prefix) }
            .forEach { scheduler.deleteJob(it) }
    }

    // 해당 날짜의 job만 삭제
    fun delete(volunteerId: UUID, scheduleDate: LocalDate) {
        val identity = "${volunteerId}_$scheduleDate"
        scheduler.deleteJob(JobKey.jobKey(identity, "volunteer-remind"))
    }
}
