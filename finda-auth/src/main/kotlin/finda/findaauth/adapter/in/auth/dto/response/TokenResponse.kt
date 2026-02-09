package finda.findaauth.adapter.`in`.auth.dto.response

import java.time.LocalDateTime

data class TokenResponse(
    val accessToken: String,
    val accessExp: LocalDateTime,
    val refreshToken: String,
    val refreshExp: LocalDateTime
)
