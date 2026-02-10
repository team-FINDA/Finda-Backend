package finda.findaauth.application.port.`in`.teacher.dto.request

data class TeacherSignupCommand(
    val preAuthToken: String,
    val email: String,
    val name: String,
    val password: String
)
