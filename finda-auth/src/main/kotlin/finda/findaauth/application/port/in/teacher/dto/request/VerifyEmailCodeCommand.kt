package finda.findaauth.application.port.`in`.teacher.dto.request

data class VerifyEmailCodeCommand(
    val preAuthToken: String,
    val email: String,
    val code: String
)
