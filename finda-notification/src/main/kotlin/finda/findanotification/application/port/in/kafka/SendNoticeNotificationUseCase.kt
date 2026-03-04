package finda.findanotification.application.port.`in`.kafka

import finda.findanotification.application.port.`in`.kafka.dto.NoticeScheduledEvent

interface SendNoticeNotificationUseCase {
    fun send(event: NoticeScheduledEvent)
}
