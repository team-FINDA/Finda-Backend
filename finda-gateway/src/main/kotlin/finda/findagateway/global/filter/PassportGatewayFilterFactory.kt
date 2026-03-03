package finda.findagateway.global.filter

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
import org.springframework.stereotype.Component
import org.springframework.util.AntPathMatcher
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

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
            // /finda-auth/students/login -> /students/login (첫 번째 세그먼트 제거)
            val pathWithoutPrefix = requestPath.substringAfter("/", "").let {
                it.substringAfter("/", "").let { path -> if (path.isEmpty()) "/" else "/$path" }
            }

            val isPermitAllPath = SecurityPath.PERMIT_ALL_PATHS.any { pathMatcher.match(it, pathWithoutPrefix) }

            if (isPermitAllPath) {
                return@GatewayFilter chain.filter(exchange)
            }

            Mono.fromCallable {
                val token = resolveToken(exchange)
                val jwtClaims = jwtProvider.validateAccessToken(token)

                // Authority 변환
                val authority = try {
                    Authority.valueOf(jwtClaims.userType)
                } catch (e: IllegalArgumentException) {
                    log.debug("Invalid authority: ${jwtClaims.userType}")
                    throw InvalidTokenException
                }

                // 타임스탬프 생성
                val now = System.currentTimeMillis()
                val expiresAt = now + 60_000 // 1분 유효 (60초)

                // Passport Integrity 생성
                val userIntegrity = PassportIntegrityUtil.generate(
                    userId = jwtClaims.userId,
                    authority = authority,
                    secretKey = passportProperties.key
                )

                // Passport 생성
                Passport(
                    userId = jwtClaims.userId,
                    authority = authority,
                    userIntegrity = userIntegrity,
                    issuedAt = now,
                    expiresAt = expiresAt
                )
            }
                .flatMap { passport ->
                    Mono.fromCallable { serializePassport(passport) }
                        .map { serializedPassport ->
                            val modifiedExchange = exchange.mutate()
                                .request {
                                    it.header(PassportSecurityProperties.PASSPORT_HEADER, serializedPassport)
                                }
                                .build()
                            modifiedExchange
                        }
                        .flatMap { modifiedExchange ->
                            chain.filter(modifiedExchange)
                        }
                }
                .onErrorResume { error ->
                    log.warn("Authentication failed for path {}: {}", requestPath, error.message)
                    val response = exchange.response
                    response.statusCode = org.springframework.http.HttpStatus.UNAUTHORIZED
                    response.setComplete()
                }
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
