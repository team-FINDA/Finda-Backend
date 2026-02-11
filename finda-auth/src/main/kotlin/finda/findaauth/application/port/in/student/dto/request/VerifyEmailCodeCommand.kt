package finda.findaauth.application.port.`in`.student.dto.request

data class VerifyEmailCodeCommand(
    val accountId: String,
    val code: String
)
