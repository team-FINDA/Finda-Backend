package finda.findanotification.adapter.`in`.notice.dto.request

import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDate
import java.time.LocalTime

data class CreateNoticeWebRequest(

    @field:NotBlank(message = "제목은 비어 있을 수 없습니다.")
    @field:Size(max = 255, message = "제목은 255자 이하여야 합니다.")
    val title: String,

    @field:NotBlank(message = "내용은 비어 있을 수 없습니다.")
    val body: String,

    val noticeDate: LocalDate?,
    val noticeTime: LocalTime?
) {
    @AssertTrue(message = "날짜와 시간은 함께 입력하거나 둘 다 비워야 합니다.")
    fun isDateTimeValid(): Boolean {
        return (noticeDate == null && noticeTime == null) ||
            (noticeDate != null && noticeTime != null)
    }
}
