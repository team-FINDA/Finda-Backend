package finda.findanotification.adapter.out.kafka.dto

import java.util.UUID

data class NoticeScheduledEvent(
    val noticeId: UUID
)
