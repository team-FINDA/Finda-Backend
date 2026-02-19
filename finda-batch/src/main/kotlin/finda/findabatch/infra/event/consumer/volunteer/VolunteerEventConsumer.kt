package finda.findabatch.infra.event.consumer.volunteer

import finda.findabatch.infra.event.dto.volunteer.VolunteerRemindEvent
import finda.findabatch.infra.event.dto.volunteer.VolunteerStatusChangedEvent
import finda.findabatch.infra.schedule.job.volunteer.VolunteerRemindJobScheduler
import finda.findabatch.infra.schedule.job.volunteer.VolunteerStatusChangedJobScheduler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class VolunteerEventConsumer(
    private val volunteerRemindJobScheduler: VolunteerRemindJobScheduler,
    private val volunteerStatusChangedJobScheduler: VolunteerStatusChangedJobScheduler
) {

    @KafkaListener(topics = ["VOLUNTEER-STATUS-CHANGED"])
    fun consumeVolunteerStatusChanged(
        @Payload event: VolunteerStatusChangedEvent,
        acknowledgment: Acknowledgment
    ) {
        volunteerStatusChangedJobScheduler.schedule(event)
        acknowledgment.acknowledge()
    }

    @KafkaListener(topics = ["VOLUNTEER-REMINED"])
    fun consumeVolunteerRemind(
        @Payload event: VolunteerRemindEvent,
        acknowledgment: Acknowledgment
    ) {
        volunteerRemindJobScheduler.schedule(event)
        acknowledgment.acknowledge()
    }
}
