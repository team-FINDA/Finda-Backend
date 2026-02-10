package finda.findaauth.application.port.`in`.teacher.dto.request

data class SendEmailVerificationCommand(
    val preAuthToken: String,
    val email: String
)
