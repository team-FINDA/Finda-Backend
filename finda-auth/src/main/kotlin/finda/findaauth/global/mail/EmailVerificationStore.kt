package finda.findaauth.global.mail

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class EmailVerificationStore(
    private val redisTemplate: RedisTemplate<String, String>
) {
    companion object {
        // Redis 키는 대소문자 구분하므로 모든 이메일을 lowercase()로 정규화하여 사용
        private const val CODE_PREFIX = "email:verification:code:"
        private const val VERIFIED_PREFIX = "email:verification:verified:"

        private const val CODE_EXPIRE_MINUTES = 5L
        private const val VERIFIED_EXPIRE_HOURS = 1L
    }

    private fun normalizeEmail(email: String): String {
        return email.lowercase()
    }

    fun saveCode(email: String, code: String) {
        redisTemplate.opsForValue().set(
            CODE_PREFIX + normalizeEmail(email),
            code,
            CODE_EXPIRE_MINUTES,
            TimeUnit.MINUTES
        )
    }

    fun getCode(email: String): String? {
        return redisTemplate.opsForValue().get(CODE_PREFIX + normalizeEmail(email))
    }

    fun deleteCode(email: String) {
        redisTemplate.delete(CODE_PREFIX + normalizeEmail(email))
    }

    fun markAsVerified(email: String) {
        redisTemplate.opsForValue().set(
            VERIFIED_PREFIX + normalizeEmail(email),
            "true",
            VERIFIED_EXPIRE_HOURS,
            TimeUnit.HOURS
        )
    }

    fun isVerified(email: String): Boolean {
        return redisTemplate.opsForValue().get(VERIFIED_PREFIX + normalizeEmail(email)) == "true"
    }

    fun deleteVerified(email: String) {
        redisTemplate.delete(VERIFIED_PREFIX + normalizeEmail(email))
    }
}
