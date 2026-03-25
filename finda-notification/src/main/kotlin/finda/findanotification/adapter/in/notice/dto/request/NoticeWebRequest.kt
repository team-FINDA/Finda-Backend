package finda.findanotification.adapter.`in`.notice.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDate
import java.time.LocalTime

data class NoticeWebRequest(

    @field:NotBlank(message = "제목은 비어 있을 수 없습니다.")
    @field:Size(max = 255, message = "제목은 255자 이하여야 합니다.")
    val title: String,

    @field:NotBlank(message = "내용은 비어 있을 수 없습니다.")
    val body: String,

    val noticeDate: LocalDate,
    val noticeTime: LocalTime
)
