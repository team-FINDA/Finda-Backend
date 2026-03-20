package finda.findanotification.adapter.out.kafka

import finda.findanotification.adapter.out.kafka.dto.NoticeCancelEvent
import finda.findanotification.adapter.out.kafka.dto.NoticeScheduledEvent
import finda.findanotification.application.port.out.kafka.CancelNoticeScheduledEventPort
import finda.findanotification.application.port.out.kafka.SendNoticeScheduledEventPort
import finda.findanotification.domain.notice.model.Notice
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class NoticeScheduledEventProducer(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) : SendNoticeScheduledEventPort, CancelNoticeScheduledEventPort {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun send(notice: Notice) {
        try {
            kafkaTemplate.send(SCHEDULE_TOPIC, NoticeScheduledEvent(notice.id)).get()
        } catch (ex: Exception) {
            log.error("Failed to send NoticeScheduledEvent: $notice", ex)
            throw ex
        }
    }

    override fun cancel(noticeId: UUID) {
        try {
            kafkaTemplate.send(CANCEL_TOPIC, NoticeCancelEvent(noticeId)).get()
        } catch (ex: Exception) {
            log.error("Failed to send NoticeCancelEvent: noticeId=$noticeId", ex)
            throw ex
        }
    }

    companion object {
        private const val SCHEDULE_TOPIC = "NOTIFICATION-SCHEDULED"
        private const val CANCEL_TOPIC = "NOTIFICATION-CANCEL"
    }
}
