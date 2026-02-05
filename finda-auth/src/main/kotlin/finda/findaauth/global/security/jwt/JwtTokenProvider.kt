package finda.findaauth.global.security.jwt

import finda.findaauth.adapter.`in`.dto.response.TokenResponse
import finda.findaauth.adapter.out.persistence.auth.entity.RefreshToken
import finda.findaauth.adapter.out.persistence.auth.repository.RefreshTokenRepository
import finda.findaauth.domain.user.model.UserType
import finda.findaauth.global.security.jwt.exception.ExpiredTokenException
import finda.findaauth.global.security.jwt.exception.InvalidTokenException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.Date
import java.util.UUID
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties,
    private val refreshTokenRepository: RefreshTokenRepository
) {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray())

    companion object {
        private const val CLAIM_TYPE = "type"
        private const val TOKEN_TYPE_ACCESS = "access"
        private const val TOKEN_TYPE_REFRESH = "refresh"

        private const val CLAIM_USER_TYPE = "userType"

        private const val MILLIS_PER_SECOND = 1000L
    }

    fun generateTokens(userId: UUID, userType: UserType) = TokenResponse(
        accessToken = generateAccessToken(userId, userType),
        accessExp = LocalDateTime.now().plusSeconds(jwtProperties.accessExp),
        refreshToken = generateRefreshToken(userId),
        refreshExp = LocalDateTime.now().plusSeconds(jwtProperties.refreshExp)
    )

    private fun generateAccessToken(userId: UUID, userType: UserType): String =
        Jwts.builder()
            .setSubject(userId.toString())
            .claim(CLAIM_TYPE, TOKEN_TYPE_ACCESS)
            .claim(CLAIM_USER_TYPE, userType.name)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtProperties.accessExp * MILLIS_PER_SECOND))
            .signWith(secretKey, SignatureAlgorithm.HS512)
            .compact()

    private fun generateRefreshToken(userId: UUID): String {
        val token = Jwts.builder()
            .setSubject(userId.toString())
            .claim(CLAIM_TYPE, TOKEN_TYPE_REFRESH)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtProperties.refreshExp * MILLIS_PER_SECOND))
            .signWith(secretKey, SignatureAlgorithm.HS512)
            .compact()

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
        val claims = getClaims(token)

        if (claims[CLAIM_TYPE] != TOKEN_TYPE_ACCESS) throw InvalidTokenException

        val userId = parseUserId(claims.subject)

        return UsernamePasswordAuthenticationToken(userId, null, emptyList())
    }

    fun validateRefreshToken(token: String): UUID {
        val claims = getClaims(token)
        if (claims[CLAIM_TYPE] != TOKEN_TYPE_REFRESH) throw InvalidTokenException

        val userId = parseUserId(claims.subject)

        val savedToken = refreshTokenRepository.findById(token)
            .orElseThrow { InvalidTokenException }

        if (savedToken.userId != userId) throw InvalidTokenException

        refreshTokenRepository.deleteById(token)

        return userId
    }

    private fun parseUserId(subject: String): UUID =
        try {
            UUID.fromString(subject)
        } catch (e: IllegalArgumentException) {
            logger.debug("Invalid UUID format: $subject")
            throw InvalidTokenException
        }

    private fun getClaims(token: String): Claims =
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: ExpiredJwtException) {
            throw ExpiredTokenException
        } catch (e: Exception) {
            logger.debug("Token validation failed: ${e.message}")
            throw InvalidTokenException
        }

    fun resolveToken(request: HttpServletRequest): String? =
        request.getHeader(jwtProperties.header)
            ?.takeIf { it.startsWith(jwtProperties.prefix) }
            ?.substring(jwtProperties.prefix.length)

            ?.takeIf { it.isNotBlank() }
}
