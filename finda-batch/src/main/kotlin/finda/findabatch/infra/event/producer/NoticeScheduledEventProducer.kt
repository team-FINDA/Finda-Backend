package finda.findabatch.infra.event.producer

import finda.findabatch.infra.event.dto.notification.NoticeScheduledFiredEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class NoticeScheduledEventProducer(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {

    private val TOPIC = "NOTICE-FIRED"

    fun produce(event: NoticeScheduledFiredEvent) {
        kafkaTemplate.send(TOPIC, event)
    }
}
