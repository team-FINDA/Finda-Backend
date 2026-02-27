package finda.findanotification.domain.notice.model

import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class Notice(
    val id: UUID,
    val title: String,
    val body: String,
    val adminId: String? = null, // gateway 추가 후 adminId 받아 저장하도록 수정, 추후 null 제거
    val noticeDate: LocalDate,
    val noticeTime: LocalTime
)
