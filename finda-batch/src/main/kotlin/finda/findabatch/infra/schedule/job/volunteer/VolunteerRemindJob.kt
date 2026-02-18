package finda.findabatch.infra.schedule.job.volunteer

import finda.findabatch.infra.event.dto.volunteer.VolunteerRemindFiredEvent
import finda.findabatch.infra.event.producer.VolunteerRemindEventProducer
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.stereotype.Component
import java.util.*

@Component
class VolunteerRemindJob(
    private val volunteerRemindEventProducer: VolunteerRemindEventProducer
) : Job {

    override fun execute(context: JobExecutionContext) {
        val volunteerId = context.jobDetail.jobDataMap.getString("volunteerId")

        volunteerRemindEventProducer.produce(
            VolunteerRemindFiredEvent(
                volunteerId = UUID.fromString(volunteerId)
            )
        )
    }
}
