package finda.findanotification.application.port.`in`.kafka

import finda.findanotification.adapter.`in`.kafka.dto.NoticeScheduledFiredEvent

interface SendNoticeNotificationUseCase {
    fun send(event: NoticeScheduledFiredEvent)
}
