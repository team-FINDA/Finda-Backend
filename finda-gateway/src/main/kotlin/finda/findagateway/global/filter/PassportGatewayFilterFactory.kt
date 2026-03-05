package finda.findagateway.global.filter

import finda.findagateway.global.error.exception.InternalServerException
import finda.findagateway.global.security.jwt.JwtProperties
import finda.findagateway.global.security.jwt.exception.InvalidTokenException
import finda.security.passport.PassportParser
import finda.security.passport.model.Authority
import finda.security.passport.model.Passport
import finda.security.passport.propertice.PassportSecurityProperties
import finda.security.passport.util.PassportIntegrityUtil
import finda.security.path.SecurityPath
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.util.AntPathMatcher
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

/**
 * Gateway에서 JWT 토큰을 검증하고 Passport로 변환하여 다운스트림 서비스에 전달하는 필터
 */
@Component
class PassportGatewayFilterFactory(
    private val jwtProvider: finda.security.jwt.JwtProvider,
    private val passportProperties: finda.findagateway.global.config.properties.PassportProperties,
    private val jwtProperties: JwtProperties
) : AbstractGatewayFilterFactory<PassportGatewayFilterFactory.Config>(Config::class.java) {

    private val pathMatcher = AntPathMatcher()
    private val log = org.slf4j.LoggerFactory.getLogger(javaClass)

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val requestPath = exchange.request.uri.path
            val pathWithoutPrefix = stripFirstPathSegment(requestPath)

            val isPermitAllPath = SecurityPath.PERMIT_ALL_PATHS.any { pathMatcher.match(it, pathWithoutPrefix) }
            if (isPermitAllPath) return@GatewayFilter chain.filter(exchange)

            authenticate(exchange)
                .map { passport -> serializePassport(passport) }
                .flatMap { serializedPassport ->
                    val modifiedExchange = exchange.mutate()
                        .request { it.header(PassportSecurityProperties.PASSPORT_HEADER, serializedPassport) }
                        .build()
                    chain.filter(modifiedExchange)
                }
                .onErrorResume { error ->
                    log.warn("Authentication failed for path {}: {}", requestPath, error.message)
                    exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                    exchange.response.setComplete()
                }
        }
    }

    private fun stripFirstPathSegment(path: String): String {
        val withoutLeadingSlash = path.removePrefix("/")
        val rest = withoutLeadingSlash.substringAfter("/", "")
        return if (rest.isBlank()) "/" else "/$rest"
    }

    private fun authenticate(exchange: ServerWebExchange): Mono<Passport> {
        return Mono.defer {
            val token = resolveToken(exchange)
            val jwtClaims = jwtProvider.validateAccessToken(token)

            val authority = try {
                Authority.valueOf(jwtClaims.userType)
            } catch (e: IllegalArgumentException) {
                log.debug("Invalid authority: {}", jwtClaims.userType)
                throw InvalidTokenException
            } catch (e: Exception) {
                log.debug("Authority 파싱 오류: {}", jwtClaims.userType)
                throw InternalServerException
            }

            val now = System.currentTimeMillis()
            val expiresAt = now + 60_000

            val userIntegrity = PassportIntegrityUtil.generate(
                userId = jwtClaims.userId,
                authority = authority,
                secretKey = passportProperties.key
            )

            Mono.just(
                Passport(
                    userId = jwtClaims.userId,
                    authority = authority,
                    userIntegrity = userIntegrity,
                    issuedAt = now,
                    expiresAt = expiresAt
                )
            )
        }
    }

    private fun resolveToken(exchange: ServerWebExchange): String {
        val authorizationHeader = exchange.request.headers.getFirst(jwtProperties.header)
            ?: throw InvalidTokenException

        if (!authorizationHeader.startsWith(jwtProperties.prefix)) {
            throw InvalidTokenException
        }

        return authorizationHeader.removePrefix(jwtProperties.prefix)
    }

    private fun serializePassport(passport: Passport): String {
        return PassportParser.serialize(passport)
    }

    class Config
}
