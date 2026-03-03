package finda.findanotification.adapter.out.kafka

import finda.findanotification.adapter.out.kafka.dto.NoticeScheduledEvent
import finda.findanotification.application.port.out.kafka.SendNoticeScheduledEventPort
import finda.findanotification.domain.notice.model.Notice
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class NoticeScheduledEventProducer(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) : SendNoticeScheduledEventPort {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun send(notice: Notice) {
        try {
            /**
             * 비동기 전송 시 실패는 로깅만 되고 호출자에게 전파되지 않아 이벤트 누락 가능 → 동기 전송으로 변경
             */
            kafkaTemplate.send(TOPIC, NoticeScheduledEvent(notice.id)).get()
        } catch (ex: Exception) {
            log.error("Failed to send NoticeScheduledEvent: $notice", ex)
            throw ex
        }
    }

    companion object {
        private const val TOPIC = "NOTIFICATION-SCHEDULED"
    }
}
