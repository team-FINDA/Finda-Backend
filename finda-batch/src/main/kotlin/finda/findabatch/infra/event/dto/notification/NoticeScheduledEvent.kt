package finda.findabatch.infra.event.dto.notification

import java.time.LocalDateTime
import java.util.UUID

data class NoticeScheduledEvent(
    val noticeId: UUID,
    val noticeDate: LocalDateTime
)
