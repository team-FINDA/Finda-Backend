package finda.findanotification.application.port.`in`.notice.dto.response

import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class NoticeResult(
    val id: UUID,
    val userName: String,
    val title: String,
    val body: String,
    val noticeDate: LocalDate?,
    val noticeTime: LocalTime?
)
