package finda.findanotification.adapter.`in`.notice.dto.request

import java.time.LocalDate
import java.time.LocalTime

data class CreateNoticeWebRequest(
    val title: String,
    val body: String,
    val noticeDate: LocalDate?,
    val noticeTime: LocalTime?
)
