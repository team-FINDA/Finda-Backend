package finda.findaauth.adapter.`in`.student.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class VerifyEmailCodeRequest(
    @field:NotBlank(message = "이메일은 필수입니다")
    @field:Email(message = "올바른 이메일 형식이 아닙니다")
    val email: String,

    @field:NotBlank(message = "인증코드는 필수입니다")
    @field:Size(min = 6, max = 6, message = "인증코드는 6자리입니다")
    val code: String
)
