package finda.findanotification.application.port.`in`.notice.dto.response

import java.time.LocalDate
import java.time.LocalTime

data class GetNoticeResult(
    val userName: String,
    val title: String,
    val content: String,
    val noticeDate: LocalDate?,
    val noticeTime: LocalTime?
)
