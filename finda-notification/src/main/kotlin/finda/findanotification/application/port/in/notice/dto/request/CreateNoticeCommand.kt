package finda.findanotification.application.port.`in`.notice.dto.request

import java.time.LocalDate
import java.time.LocalTime

data class CreateNoticeCommand(
    val title: String,
    val body: String,
    val adminId: String,
    val noticeDate: LocalDate?,
    val noticeTime: LocalTime?
)
