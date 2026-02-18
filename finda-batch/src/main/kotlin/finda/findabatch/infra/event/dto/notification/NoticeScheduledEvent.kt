package finda.findabatch.infra.event.dto.notification

import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class NoticeScheduledEvent(
    val noticeId: UUID,
    val noticeDate: LocalDate,
    val noticeTime: LocalTime
)
