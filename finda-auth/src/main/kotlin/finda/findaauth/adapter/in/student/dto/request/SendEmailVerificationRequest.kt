package finda.findaauth.adapter.`in`.student.dto.request

import jakarta.validation.constraints.NotBlank

data class SendEmailVerificationRequest(
    @field:NotBlank(message = "이메일은 필수입니다")
    val email: String
)
