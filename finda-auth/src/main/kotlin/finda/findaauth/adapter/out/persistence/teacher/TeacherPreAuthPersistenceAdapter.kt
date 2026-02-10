package finda.findaauth.adapter.out.persistence.teacher

import finda.findaauth.application.port.out.teacher.TeacherPreAuthCommandPort
import finda.findaauth.application.port.out.teacher.TeacherPreAuthQueryPort
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class TeacherPreAuthPersistenceAdapter(
    private val redisTemplate: StringRedisTemplate
) : TeacherPreAuthCommandPort, TeacherPreAuthQueryPort {

    companion object {
        private const val PREFIX = "pre-auth-token:"
        private const val TTL = 10L
    }

    override fun save(token: String) {
        redisTemplate.opsForValue()
            .set("$PREFIX$token", "1", TTL, TimeUnit.MINUTES)
    }

    override fun delete(token: String) {
        redisTemplate.delete("$PREFIX$token")
    }

    override fun isValid(token: String): Boolean {
        return redisTemplate.hasKey("$PREFIX$token")
    }
}
