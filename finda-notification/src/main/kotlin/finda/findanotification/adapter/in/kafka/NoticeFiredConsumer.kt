package finda.findanotification.adapter.`in`.kafka

import finda.findanotification.application.port.`in`.kafka.SendNoticeNotificationUseCase
import finda.findanotification.application.port.`in`.kafka.dto.NoticeScheduledEvent
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class NoticeFiredConsumer(
    private val sendNoticeNotificationUseCase: SendNoticeNotificationUseCase
) {

    @KafkaListener(topics = ["NOTICE-FIRED"])
    fun consumeNoticeFired(
        @Payload event: NoticeScheduledEvent,
        acknowledgment: Acknowledgment
    ) {
        sendNoticeNotificationUseCase.send(event)
        acknowledgment.acknowledge()
    }
}
