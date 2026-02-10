package finda.findaauth.application.port.`in`.student.dto.request

data class StudentLoginCommand(
    val accountId: String,
    val password: String
)
