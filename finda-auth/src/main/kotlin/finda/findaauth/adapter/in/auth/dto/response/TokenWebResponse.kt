package finda.findaauth.adapter.`in`.auth.dto.response

import finda.findaauth.application.port.`in`.auth.dto.response.TokenResult
import java.time.LocalDateTime

data class TokenWebResponse(
    val accessToken: String,
    val accessExp: LocalDateTime,
    val refreshToken: String,
    val refreshExp: LocalDateTime
) {
    companion object {
        fun from(result: TokenResult): TokenWebResponse {
            return TokenWebResponse(
                accessToken = result.accessToken,
                accessExp = result.accessExp,
                refreshToken = result.refreshToken,
                refreshExp = result.refreshExp
            )
        }
    }
}
