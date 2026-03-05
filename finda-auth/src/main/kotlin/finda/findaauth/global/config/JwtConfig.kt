package finda.findaauth.global.config

import finda.findaauth.global.security.jwt.JwtProperties
import finda.security.jwt.JwtProvider
import io.jsonwebtoken.security.Keys
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JwtConfig {

    @Bean
    fun jwtProvider(jwtProperties: JwtProperties): JwtProvider {
        val secretKey = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray(Charsets.UTF_8))
        return JwtProvider(secretKey)
    }
}
