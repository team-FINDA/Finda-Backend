package finda.findaauth.global.security.jwt

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val secret: String,
    val accessExp: Long,
    val refreshExp: Long,
    val header: String,
    val prefix: String
)
