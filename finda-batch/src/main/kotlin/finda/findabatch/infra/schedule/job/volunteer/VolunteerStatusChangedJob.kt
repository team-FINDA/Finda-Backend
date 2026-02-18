package finda.findabatch.infra.schedule.job.volunteer

import finda.findabatch.domain.volunteer.VolunteerProgress
import finda.findabatch.domain.volunteer.VolunteerStatus
import finda.findabatch.infra.event.dto.volunteer.VolunteerStatusChangedFiredEvent
import finda.findabatch.infra.event.producer.VolunteerStatusChangedEventProducer
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class VolunteerStatusChangedJob(
    private val volunteerStatusChangedEventProducer: VolunteerStatusChangedEventProducer
) : Job {

    override fun execute(context: JobExecutionContext) {
        val volunteerId = context.jobDetail.jobDataMap.getString("volunteerId")
        val status = context.jobDetail.jobDataMap.getString("status")
        val progress = context.jobDetail.jobDataMap.getString("progress")

        volunteerStatusChangedEventProducer.produce(
            VolunteerStatusChangedFiredEvent(
                volunteerId = UUID.fromString(volunteerId),
                status = VolunteerStatus.valueOf(status),
                progress = VolunteerProgress.valueOf(progress)
            )
        )
    }
}
