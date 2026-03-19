package finda.findanotification.application.port.`in`.notice.dto.request

import finda.findanotification.domain.notice.model.Notice
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class NoticeCommand(
    val title: String,
    val body: String,
    val userId: UUID,
    val noticeDate: LocalDate,
    val noticeTime: LocalTime
)
