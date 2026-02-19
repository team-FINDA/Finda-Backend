package finda.findabatch.infra.schedule.job.volunteer

import finda.findabatch.domain.volunteer.VolunteerProgress
import finda.findabatch.domain.volunteer.VolunteerStatus
import finda.findabatch.infra.event.dto.volunteer.VolunteerStatusChangedFiredEvent
import finda.findabatch.infra.event.producer.VolunteerStatusChangedEventProducer
import finda.findabatch.infra.schedule.job.BaseQuartzJob
import finda.findabatch.infra.schedule.job.requireEnum
import finda.findabatch.infra.schedule.job.requireUuid
import org.quartz.JobExecutionContext
import org.springframework.stereotype.Component

@Component
class VolunteerStatusChangedJob(
    private val volunteerStatusChangedEventProducer: VolunteerStatusChangedEventProducer
) : BaseQuartzJob() {

    override fun doExecute(context: JobExecutionContext) {
        val volunteerId = context.requireUuid("volunteerId")
        val status = context.requireEnum<VolunteerStatus>("status")
        val progress = context.requireEnum<VolunteerProgress>("progress")

        volunteerStatusChangedEventProducer.produce(
            VolunteerStatusChangedFiredEvent(
                volunteerId = volunteerId,
                status = status,
                progress = progress
            )
        )
    }
}
