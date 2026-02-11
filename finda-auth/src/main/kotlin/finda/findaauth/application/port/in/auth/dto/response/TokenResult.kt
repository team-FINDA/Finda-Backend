package finda.findaauth.application.port.`in`.auth.dto.response

import java.time.LocalDateTime

data class TokenResult(
    val accessToken: String,
    val accessExp: LocalDateTime,
    val refreshToken: String,
    val refreshExp: LocalDateTime
)
