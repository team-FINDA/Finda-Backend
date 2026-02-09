package finda.findaauth.global.mail

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class EmailVerificationStore(
    private val redisTemplate: RedisTemplate<String, String>
) {
    companion object {
        private const val CODE_PREFIX = "email:verification:code:"
        private const val VERIFIED_PREFIX = "email:verification:verified:"

        private const val CODE_EXPIRE_MINUTES = 5L
        private const val VERIFIED_EXPIRE_HOURS = 1L
    }

    fun saveCode(email: String, code: String) {
        redisTemplate.opsForValue().set(
            CODE_PREFIX + email,
            code,
            CODE_EXPIRE_MINUTES,
            TimeUnit.MINUTES
        )
    }

    fun getCode(email: String): String? {
        return redisTemplate.opsForValue().get(CODE_PREFIX + email)
    }

    fun deleteCode(email: String) {
        redisTemplate.delete(CODE_PREFIX + email)
    }

    fun markAsVerified(email: String) {
        redisTemplate.opsForValue().set(
            VERIFIED_PREFIX + email,
            "true",
            VERIFIED_EXPIRE_HOURS,
            TimeUnit.HOURS
        )
    }

    fun isVerified(email: String): Boolean {
        return redisTemplate.opsForValue().get(VERIFIED_PREFIX + email) == "true"
    }

    fun deleteVerified(email: String) {
        redisTemplate.delete(VERIFIED_PREFIX + email)
    }
}
