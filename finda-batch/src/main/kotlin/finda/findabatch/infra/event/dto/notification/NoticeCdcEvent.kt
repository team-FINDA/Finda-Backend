package finda.findabatch.infra.event.dto.notification

import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class NoticeCdcEvent(
    val before: NoticeSnapshot?,
    val after: NoticeSnapshot?,
    val op: String
)

data class NoticeSnapshot(
    val id: UUID,
    val title: String,
    val body: String,
    val userId: UUID,
    val status: String,
    val noticeDate: LocalDate,
    val noticeTime: LocalTime
)
