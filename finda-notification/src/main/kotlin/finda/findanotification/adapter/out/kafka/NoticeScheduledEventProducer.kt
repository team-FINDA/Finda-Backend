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
        kafkaTemplate.send(TOPIC, NoticeScheduledEvent(notice.id))
            .whenComplete { _, ex ->
                if (ex != null) log.error("Failed to send NoticeScheduledEvent: $notice", ex)
            }
    }

    companion object {
        private const val TOPIC = "NOTIFICATION-SCHEDULED"
    }
}
