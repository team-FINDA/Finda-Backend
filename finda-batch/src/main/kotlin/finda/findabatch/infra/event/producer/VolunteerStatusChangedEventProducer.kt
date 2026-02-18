package finda.findabatch.infra.event.producer

import finda.findabatch.infra.event.dto.volunteer.VolunteerStatusChangedFiredEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class VolunteerStatusChangedEventProducer(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {

    private val TOPIC = "VOLUNTEER-STATUS-FIRED"

    fun produce(event: VolunteerStatusChangedFiredEvent) {
        kafkaTemplate.send(TOPIC, event)
    }
}
