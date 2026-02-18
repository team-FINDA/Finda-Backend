package finda.findabatch.infra.event.dto.notification

import java.util.UUID

data class NoticeScheduledFiredEvent(
    val noticeId: UUID
)
