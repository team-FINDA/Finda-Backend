package finda.findaauth.adapter.out.persistence.auth

import finda.findaauth.application.port.out.TeacherSignupTokenPort
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class AuthPersistenceAdapter(
    private val redisTemplate: StringRedisTemplate
) : TeacherSignupTokenPort {

    companion object {
        private const val TTL = 10L
    }

    override fun save(token: String) {
        redisTemplate.opsForValue()
            .set("teacher-signup:$token", "1", TTL, TimeUnit.MINUTES)
    }
}
