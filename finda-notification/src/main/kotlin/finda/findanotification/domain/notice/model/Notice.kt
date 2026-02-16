package finda.findanotification.domain.notice.model

import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class Notice(
    val id: UUID,
    val title: String,
    val body: String,
    val adminId: String,
    val noticeDate: LocalDate,
    val noticeTime: LocalTime
)
