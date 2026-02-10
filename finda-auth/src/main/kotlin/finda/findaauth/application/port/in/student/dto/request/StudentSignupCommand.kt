package finda.findaauth.application.port.`in`.student.dto.request

data class StudentSignupCommand(
    val accountId: String,
    val studentInfo: String,
    val password: String
)
