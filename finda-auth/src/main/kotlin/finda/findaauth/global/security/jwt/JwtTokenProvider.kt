package finda.findaauth.global.security.jwt

import finda.findaauth.adapter.out.persistence.auth.entity.RefreshToken
import finda.findaauth.adapter.out.persistence.auth.repository.RefreshTokenRepository
import finda.findaauth.application.port.`in`.auth.dto.response.TokenResult
import finda.findaauth.domain.user.model.UserType
import finda.findaauth.global.security.jwt.exception.InvalidTokenException
import finda.security.jwt.JwtProvider
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.UUID
import finda.security.jwt.exception.ExpiredTokenException as CommonExpiredTokenException
import finda.security.jwt.exception.InvalidTokenException as CommonInvalidTokenException

@Component
class JwtTokenProvider(
    private val jwtProvider: JwtProvider,
    private val jwtProperties: JwtProperties,
    private val refreshTokenRepository: RefreshTokenRepository
) {

    fun generateTokens(userId: UUID, userType: UserType) = TokenResult(
        accessToken = jwtProvider.generateAccessToken(
            userId = userId,
            userType = userType.name,
            expirationSeconds = jwtProperties.accessExp
        ),
        accessExp = LocalDateTime.now().plusSeconds(jwtProperties.accessExp),
        refreshToken = generateAndSaveRefreshToken(userId, userType),
        refreshExp = LocalDateTime.now().plusSeconds(jwtProperties.refreshExp)
    )

    private fun generateAndSaveRefreshToken(userId: UUID, userType: UserType): String {
        val token = jwtProvider.generateRefreshToken(
            userId = userId,
            userType = userType.name,
            expirationSeconds = jwtProperties.refreshExp
        )

        refreshTokenRepository.save(
            RefreshToken(
                token = token,
                userId = userId,
                ttl = jwtProperties.refreshExp
            )
        )

        return token
    }

    fun getAuthentication(token: String): Authentication {
        val claims = try {
            jwtProvider.validateAccessToken(token)
        } catch (e: CommonInvalidTokenException) {
            throw InvalidTokenException
        } catch (e: CommonExpiredTokenException) {
            throw finda.findaauth.global.security.jwt.exception.ExpiredTokenException
        }
        return UsernamePasswordAuthenticationToken(claims.userId, null, emptyList())
    }

    fun validateRefreshToken(token: String): RefreshTokenClaims {
        val claims = try {
            jwtProvider.validateRefreshToken(token)
        } catch (e: CommonInvalidTokenException) {
            throw InvalidTokenException
        } catch (e: CommonExpiredTokenException) {
            throw finda.findaauth.global.security.jwt.exception.ExpiredTokenException
        }

        val savedToken = refreshTokenRepository.findById(token)
            .orElseThrow { InvalidTokenException }

        if (savedToken.userId != claims.userId) throw InvalidTokenException

        return try {
            RefreshTokenClaims(
                userId = claims.userId,
                userType = UserType.valueOf(claims.userType)
            )
        } catch (e: IllegalArgumentException) {
            throw InvalidTokenException
        }
    }

    fun resolveToken(request: HttpServletRequest): String? =
        request.getHeader(jwtProperties.header)
            ?.takeIf { it.startsWith(jwtProperties.prefix) }
            ?.substring(jwtProperties.prefix.length)
            ?.takeIf { it.isNotBlank() }

    fun deleteRefreshToken(token: String) {
        refreshTokenRepository.deleteById(token)
    }
}
