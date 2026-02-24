package finda.findanotification.adapter.`in`.kafka

import finda.findanotification.adapter.`in`.kafka.dto.VolunteerRemindFiredEvent
import finda.findanotification.adapter.`in`.kafka.dto.VolunteerStatusChangedFiredEvent
import finda.findanotification.application.port.`in`.kafka.SendVolunteerNotificationUseCase
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload

import org.springframework.stereotype.Component

@Component
class VolunteerFiredConsumer(
    private val sendVolunteerNotificationUseCase: SendVolunteerNotificationUseCase
) {

    @KafkaListener(topics = ["VOLUNTEER-STATUS-FIRED"])
    fun consumeVolunteerStatusChanged(
        @Payload event: VolunteerStatusChangedFiredEvent,
        acknowledgment: Acknowledgment
    ) {
        sendVolunteerNotificationUseCase.sendStatusChanged(event)
        acknowledgment.acknowledge()
    }

    @KafkaListener(topics = ["VOLUNTEER-REMINED-FIRED"])
    fun consumeVolunteerRemind(
        @Payload event: VolunteerRemindFiredEvent,
        acknowledgment: Acknowledgment
    ) {
        sendVolunteerNotificationUseCase.sendRemind(event)
        acknowledgment.acknowledge()
    }
}
