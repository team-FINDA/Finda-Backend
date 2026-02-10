package finda.findaauth.adapter.`in`.student.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class StudentSignupWebRequest(
    @field:NotBlank(message = "accountId는 필수입니다")
    val accountId: String,

    @field:NotBlank(message = "학번 정보는 필수입니다")
    @field:Pattern(
        regexp = "^\\d{4}\\s.+$",
        message = "학번은 '1101 홍길동' 형식이어야 합니다"
    )
    val studentInfo: String,

    @field:NotBlank(message = "비밀번호는 필수입니다")
    @field:Size(min = 8, message = "비밀번호는 최소 8자입니다")
    val password: String
)
