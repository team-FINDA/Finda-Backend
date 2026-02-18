package finda.findabatch.infra.schedule.job.volunteer

import finda.findabatch.domain.volunteer.VolunteerProgress
import finda.findabatch.domain.volunteer.VolunteerStatus
import finda.findabatch.infra.event.dto.volunteer.VolunteerStatusChangedEvent
import org.quartz.JobBuilder
import org.quartz.JobKey
import org.quartz.Scheduler
import org.quartz.SimpleScheduleBuilder
import org.quartz.TriggerBuilder
import org.quartz.TriggerKey
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.Date
import java.util.UUID

@Component
class VolunteerStatusChangedJobScheduler(
    private val scheduler: Scheduler
) {

    fun schedule(event: VolunteerStatusChangedEvent) {
        scheduleJob(event.volunteerId, VolunteerStatus.APPLICATION, VolunteerProgress.START, event.applicationStartDate)
        scheduleJob(event.volunteerId, VolunteerStatus.APPLICATION, VolunteerProgress.END, event.applicationEndDate)
        scheduleJob(event.volunteerId, VolunteerStatus.WORK, VolunteerProgress.START, event.workStartDate)
        scheduleJob(event.volunteerId, VolunteerStatus.WORK, VolunteerProgress.END, event.workEndDate)
    }

    private fun scheduleJob(
        volunteerId: UUID,
        status: VolunteerStatus,
        progress: VolunteerProgress,
        date: LocalDate
    ) {
        val identity = "${volunteerId}_${status}_$progress"
        val jobKey = JobKey.jobKey(identity, "volunteer-status")
        val triggerKey = TriggerKey.triggerKey(identity, "volunteer-status")

        val jobDetail = JobBuilder.newJob(VolunteerStatusChangedJob::class.java)
            .withIdentity(jobKey)
            .usingJobData("volunteerId", volunteerId.toString())
            .usingJobData("status", status.name)
            .usingJobData("progress", progress.name)
            .build()

        val triggerTime = date.atTime(LocalTime.of(8, 0))
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
            else -> scheduler.scheduleJob(jobDetail, trigger)
        }
    }

    fun delete(volunteerId: UUID) {
        TODO()
    }
}
