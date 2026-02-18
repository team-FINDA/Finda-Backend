package finda.findabatch.infra.event.producer

import finda.findabatch.infra.event.dto.volunteer.VolunteerStatusChangedFiredEvent
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class VolunteerStatusChangedEventProducer(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun produce(event: VolunteerStatusChangedFiredEvent) {
        kafkaTemplate.send(TOPIC, event).whenComplete { _, ex ->
            if (ex != null) log.error("Failed to send VolunteerStatusChangedFiredEvent: $event", ex)
        }
    }

    companion object {
        private const val TOPIC = "VOLUNTEER-STATUS-FIRED"
    }
}
