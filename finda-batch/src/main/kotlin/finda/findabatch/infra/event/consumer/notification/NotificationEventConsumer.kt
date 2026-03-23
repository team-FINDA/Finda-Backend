package finda.findabatch.infra.event.consumer.notification

import com.fasterxml.jackson.databind.ObjectMapper
import finda.findabatch.infra.event.dto.notification.NoticeCdcEvent
import finda.findabatch.infra.schedule.job.notification.NoticeScheduledJobScheduler
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class NotificationEventConsumer(
    private val noticeScheduledJobScheduler: NoticeScheduledJobScheduler,
    private val objectMapper: ObjectMapper
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @KafkaListener(
        topics = ["notification.finda_notification.tbl_notice"],
        containerFactory = "cdcKafkaListenerContainerFactory"
    )
    fun consumeNotice(
        message: String,
        acknowledgment: Acknowledgment
    ) {
        try {
            val root = objectMapper.readTree(message)
            val payload = root["payload"]

            if (payload == null || payload.isNull) {
                acknowledgment.acknowledge()
                return
            }

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

            acknowledgment.acknowledge()
        } catch (e: Exception) {
            log.error("CDC notice processing failed", e)
        }
    }
}
