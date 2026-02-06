package finda.findaauth.application.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "teacher")
data class TeacherSignupProperties(
    val signupSecret: String
)
