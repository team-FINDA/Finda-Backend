package finda.findabatch.infra.schedule.job.notification

import finda.findabatch.infra.event.dto.notification.NoticeSnapshot
import org.quartz.JobBuilder
import org.quartz.JobKey
import org.quartz.Scheduler
import org.quartz.SimpleScheduleBuilder
import org.quartz.TriggerBuilder
import org.quartz.TriggerKey
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.UUID

@Component
class NoticeScheduledJobScheduler(
    private val scheduler: Scheduler
) {

    fun schedule(snapshot: NoticeSnapshot) {
        val identity = snapshot.id.toString()
        val jobKey = JobKey.jobKey(identity, "notice-scheduled")
        val triggerKey = TriggerKey.triggerKey(identity, "notice-scheduled")

        val jobDetail = JobBuilder.newJob(NoticeScheduledJob::class.java)
            .withIdentity(jobKey)
            .usingJobData("noticeId", snapshot.id.toString())
            .build()

        val triggerTime = LocalDateTime.of(snapshot.noticeDate, snapshot.noticeTime)
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

    fun cancel(noticeId: UUID) {
        val jobKey = JobKey.jobKey(noticeId.toString(), "notice-scheduled")
        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey)
        }
    }
}
