package finda.findaauth.adapter.out.persistence.auth

import finda.findaauth.application.port.out.auth.AuthCommandPort
import finda.findaauth.application.port.out.auth.AuthQueryPort
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class AuthPersistenceAdapter(
    private val redisTemplate: StringRedisTemplate
) : AuthCommandPort, AuthQueryPort {

    companion object {
        private const val PREFIX = "pre-auth-token:"
        private const val TTL = 10L
    }

    override fun save(token: String) {
        redisTemplate.opsForValue()
            .set("$PREFIX$token", "1", TTL, TimeUnit.MINUTES)
    }

    override fun isValid(token: String): Boolean {
        return redisTemplate.hasKey("$PREFIX$token")
    }
}
