package finda.findabatch.infra.event.producer

import finda.findabatch.infra.event.dto.volunteer.VolunteerRemindFiredEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class VolunteerRemindEventProducer(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {
    private val TOPIC = "VOLUNTEER-REMINED-FIRED"

    // 같은 파티션 보장 필요 없음으로 key를 설정하지 않음
    fun produce(event: VolunteerRemindFiredEvent) {
        kafkaTemplate.send(TOPIC, event)
    }
}
