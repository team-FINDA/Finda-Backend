package finda.findagateway.global.config

import finda.findagateway.global.security.jwt.JwtProperties
import finda.security.jwt.JwtProvider
import io.jsonwebtoken.security.Keys
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JwtConfig {

    @Bean
    fun jwtProvider(jwtProperties: JwtProperties): JwtProvider {
        val secretKey = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray())
        return JwtProvider(secretKey)
    }
}
