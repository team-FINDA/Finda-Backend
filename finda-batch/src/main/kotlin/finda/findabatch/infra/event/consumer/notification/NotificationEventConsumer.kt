package finda.findabatch.infra.event.consumer.notification

import finda.findabatch.infra.event.dto.notification.NoticeScheduledEvent
import finda.findabatch.infra.schedule.job.notification.NoticeScheduledJobScheduler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class NotificationEventConsumer(
    private val noticeScheduledJobScheduler: NoticeScheduledJobScheduler
) {

    @KafkaListener(topics = ["NOTIFICATION-SCHEDULED"])
    fun consumeNoticeScheduled(
        @Payload event: NoticeScheduledEvent,
        acknowledgment: Acknowledgment
    ) {
        noticeScheduledJobScheduler.schedule(event)
        acknowledgment.acknowledge()
    }
}
