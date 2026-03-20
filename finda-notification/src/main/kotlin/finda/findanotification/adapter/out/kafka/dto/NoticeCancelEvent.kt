package finda.findanotification.adapter.out.kafka.dto

import java.util.UUID

data class NoticeCancelEvent(
    val noticeId: UUID
)
