package finda.findabatch.infra.event.consumer.notification

import com.fasterxml.jackson.databind.ObjectMapper
import finda.findabatch.infra.event.dto.notification.NoticeCdcEvent
import finda.findabatch.infra.schedule.job.notification.NoticeScheduledJobScheduler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class NotificationEventConsumer(
    private val noticeScheduledJobScheduler: NoticeScheduledJobScheduler,
    private val objectMapper: ObjectMapper
) {

    @KafkaListener(topics = ["notification.finda_notification.tbl_notice"])
    fun consumeNotice(
        message: String,
        acknowledgment: Acknowledgment
    ) {
        try {
            val root = objectMapper.readTree(message)
            val payload = root["payload"]

            val event =
                objectMapper.treeToValue(payload, NoticeCdcEvent::class.java)

            when (event.op) {
                "c", "u" -> event.after?.let {
                    noticeScheduledJobScheduler.schedule(it)
                }

                "d" -> event.before?.let {
                    noticeScheduledJobScheduler.cancel(it.id)
                }
            }
        } finally {
            acknowledgment.acknowledge()
        }
    }
}
