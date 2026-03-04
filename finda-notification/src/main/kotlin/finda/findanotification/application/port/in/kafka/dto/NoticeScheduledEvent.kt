package finda.findanotification.application.port.`in`.kafka.dto

import java.util.UUID

data class NoticeScheduledEvent(
    val noticeId: UUID
)
