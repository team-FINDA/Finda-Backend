package finda.security.jwt

import finda.security.jwt.exception.ExpiredTokenException
import finda.security.jwt.exception.InvalidTokenException
import finda.security.properties.SecurityObject
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.slf4j.LoggerFactory
import java.util.Date
import java.util.UUID
import javax.crypto.SecretKey

/**
 * JWT 토큰 생성 및 검증을 담당하는 공통 Provider
 * auth 모듈의 JwtTokenProvider와 동일한 순수 JWT 로직
 */
class JwtProvider(
    private val secretKey: SecretKey
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun generateAccessToken(
        userId: UUID,
        userType: String,
        expirationSeconds: Long
    ): String {
        val now = Date()
        val expiration = Date(System.currentTimeMillis() + expirationSeconds * SecurityObject.MILLIS_PER_SECOND)

        return Jwts.builder()
            .setSubject(userId.toString())
            .claim(SecurityObject.CLAIM_TYPE, SecurityObject.TOKEN_TYPE_ACCESS)
            .claim(SecurityObject.CLAIM_USER_TYPE, userType)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(secretKey, SignatureAlgorithm.HS512)
            .compact()
    }

    fun generateRefreshToken(
        userId: UUID,
        userType: String,
        expirationSeconds: Long
    ): String {
        val now = Date()
        val expiration = Date(System.currentTimeMillis() + expirationSeconds * SecurityObject.MILLIS_PER_SECOND)

        return Jwts.builder()
            .setSubject(userId.toString())
            .claim(SecurityObject.CLAIM_TYPE, SecurityObject.TOKEN_TYPE_REFRESH)
            .claim(SecurityObject.CLAIM_USER_TYPE, userType)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(secretKey, SignatureAlgorithm.HS512)
            .compact()
    }

    private fun parseToken(token: String): Claims {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: ExpiredJwtException) {
            logger.debug("Token expired: ${e.message}")
            throw ExpiredTokenException
        } catch (e: Exception) {
            logger.debug("Token validation failed: ${e.message}")
            throw InvalidTokenException
        }
    }

    fun validateAccessToken(token: String): JwtClaims {
        val claims = parseToken(token)

        if (claims[SecurityObject.CLAIM_TYPE] != SecurityObject.TOKEN_TYPE_ACCESS) {
            throw InvalidTokenException
        }

        return extractClaims(claims)
    }

    fun validateRefreshToken(token: String): JwtClaims {
        val claims = parseToken(token)

        if (claims[SecurityObject.CLAIM_TYPE] != SecurityObject.TOKEN_TYPE_REFRESH) {
            throw InvalidTokenException
        }

        return extractClaims(claims)
    }

    private fun extractClaims(claims: Claims): JwtClaims {
        val userId = try {
            UUID.fromString(claims.subject)
        } catch (e: IllegalArgumentException) {
            logger.debug("Invalid UUID format: ${claims.subject}")
            throw InvalidTokenException
        }

        val userType = claims[SecurityObject.CLAIM_USER_TYPE] as? String
            ?: throw InvalidTokenException

        return JwtClaims(
            userId = userId,
            userType = userType,
            claims = claims
        )
    }
}
