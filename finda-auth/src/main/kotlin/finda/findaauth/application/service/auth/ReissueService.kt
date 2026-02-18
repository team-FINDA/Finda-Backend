package finda.findaauth.application.service.auth

import finda.findaauth.application.port.`in`.auth.ReissueUseCase
import finda.findaauth.application.port.`in`.auth.dto.response.TokenResult
import finda.findaauth.global.security.jwt.JwtTokenProvider
import org.springframework.stereotype.Service

@Service
class ReissueService(
    private val jwtTokenProvider: JwtTokenProvider
) : ReissueUseCase {

    override fun execute(token: String): TokenResult {
        val (userId, userType) = jwtTokenProvider.validateRefreshToken(token)
        return jwtTokenProvider.generateTokens(userId, userType)
    }
}
