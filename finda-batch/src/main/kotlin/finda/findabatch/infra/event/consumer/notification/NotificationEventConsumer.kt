package finda.findabatch.infra.event.consumer.notification

import finda.findabatch.infra.event.dto.notification.NoticeCdcEvent
import finda.findabatch.infra.schedule.job.notification.NoticeScheduledJobScheduler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class NotificationEventConsumer(
    private val noticeScheduledJobScheduler: NoticeScheduledJobScheduler
) {

    @KafkaListener(topics = ["notification.finda_notification.tbl_notice"])
    fun consumeNotice(
        @Payload event: NoticeCdcEvent,
        acknowledgment: Acknowledgment
    ) {
        try {
            when (event.op) {
                "c", "u" -> event.after?.let { noticeScheduledJobScheduler.schedule(it) }
                "d" -> event.before?.let { noticeScheduledJobScheduler.cancel(it.id) }
                else -> {}
            }
        } finally {
            acknowledgment.acknowledge()
        }
    }
}
