package finda.findabatch.infra.schedule.job.volunteer

import finda.findabatch.infra.event.dto.volunteer.VolunteerRemindFiredEvent
import finda.findabatch.infra.event.producer.VolunteerRemindEventProducer
import finda.findabatch.infra.schedule.job.BaseQuartzJob
import finda.findabatch.infra.schedule.job.requireUuid
import org.quartz.JobExecutionContext
import org.springframework.stereotype.Component

@Component
class VolunteerRemindJob(
    private val volunteerRemindEventProducer: VolunteerRemindEventProducer
) : BaseQuartzJob() {

    override fun doExecute(context: JobExecutionContext) {
        val volunteerId = context.requireUuid("volunteerId")

        volunteerRemindEventProducer.produce(
            VolunteerRemindFiredEvent(volunteerId = volunteerId)
        )
    }
}
