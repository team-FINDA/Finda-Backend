package finda.findaauth.adapter.`in`.student.dto.request

import jakarta.validation.constraints.NotBlank

data class SendEmailVerificationRequest(
    @field:NotBlank(message = "accountId는 필수입니다")
    val accountId: String
)
