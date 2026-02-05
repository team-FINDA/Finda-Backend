package finda.findagateway.global.security.jwt

import finda.findagateway.domain.model.UserType
import finda.findagateway.global.security.principle.CustomUserDetails
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.util.UUID
import javax.crypto.SecretKey

@Component
class JwtAuthenticationWebFilter(
    private val jwtProperties: JwtProperties
) : WebFilter {

    private val log = LoggerFactory.getLogger(javaClass)

    private val secretKey: SecretKey = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray())

    companion object {
        private const val CLAIM_USER_TYPE = "userType"
    }

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val token = extractToken(exchange) ?: return chain.filter(exchange)

        return authenticateWithToken(token, exchange, chain)
    }

    private fun extractToken(exchange: ServerWebExchange): String? {
        return exchange.request.headers.getFirst(jwtProperties.header)
            ?.takeIf { it.startsWith(jwtProperties.prefix) }
            ?.substring(jwtProperties.prefix.length)
            ?.takeIf { it.isNotBlank() }
    }

    private fun authenticateWithToken(
        token: String,
        exchange: ServerWebExchange,
        chain: WebFilterChain
    ): Mono<Void> {
        return try {
            val claims = parseJwt(token)

            val userId = parseUserId(claims.subject)
                ?: return respondWithUnauthorized(exchange, "Invalid user ID format")

            val userTypeStr = claims[CLAIM_USER_TYPE]?.toString()
                ?: return respondWithUnauthorized(exchange, "Missing user type")

            val userType = try {
                UserType.valueOf(userTypeStr)
            } catch (e: IllegalArgumentException) {
                return respondWithUnauthorized(exchange, "Invalid user type: $userTypeStr")
            }

            val isStudent = userType == UserType.STUDENT
            val isTeacher = userType == UserType.TEACHER

            val userDetails = CustomUserDetails(
                userId = userId,
                username = userId.toString(),
                password = "",
                isStudent = isStudent,
                isTeacher = isTeacher
            )

            val auth = UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.authorities
            )

            chain.filter(exchange)
                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth))
                .doOnSuccess {
                    log.debug(
                        "Authenticated user {} as {} for URI: {}",
                        userId,
                        userType,
                        exchange.request.uri.path
                    )
                }
        } catch (e: ExpiredJwtException) {
            log.warn("Expired JWT for URI: {}", exchange.request.uri.path)
            respondWithUnauthorized(exchange, "Token expired")
        } catch (e: SignatureException) {
            log.warn("Invalid JWT signature for URI: {}", exchange.request.uri.path)
            respondWithUnauthorized(exchange, "Invalid token signature")
        } catch (e: Exception) {
            log.error(
                "JWT validation failed for URI: {}: {}",
                exchange.request.uri.path,
                e.message
            )
            respondWithUnauthorized(exchange, "Authentication failed")
        }
    }

    private fun parseUserId(subject: String): UUID? {
        return try {
            UUID.fromString(subject)
        } catch (e: IllegalArgumentException) {
            log.debug("Invalid UUID format: {}", subject)
            null
        }
    }

    private fun parseJwt(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
    }

    private fun respondWithUnauthorized(
        exchange: ServerWebExchange,
        reason: String
    ): Mono<Void> {
        log.debug("Unauthorized access: {}", reason)
        exchange.response.statusCode = HttpStatus.UNAUTHORIZED

        return exchange.response.setComplete()
    }
}
