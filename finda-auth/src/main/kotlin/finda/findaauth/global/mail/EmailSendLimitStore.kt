package finda.findaauth.global.mail

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit

@Component
class EmailSendLimitStore(
    private val redisTemplate: RedisTemplate<String, String>
) {
    companion object {
        // Redis 키는 대소문자 구분하므로 모든 이메일을 lowercase()로 정규화하여 사용
        private const val COUNT_PREFIX = "email:send:count:"
        private const val MAX_SEND_COUNT = 5
    }

    private fun normalizeEmail(email: String): String {
        return email.lowercase()
    }

    private fun getTodayKey(email: String): String {
        val today = LocalDate.now()
        return "$COUNT_PREFIX${normalizeEmail(email)}:$today"
    }

    private fun getSecondsUntilMidnight(): Long {
        val now = LocalDateTime.now()
        val midnight = now.toLocalDate().plusDays(1).atStartOfDay()
        return ChronoUnit.SECONDS.between(now, midnight)
    }

    fun incrementAndGet(email: String): Long {
        val key = getTodayKey(email)
        val count = redisTemplate.opsForValue().increment(key) ?: 1L

        if (count == 1L) {
            val secondsUntilMidnight = getSecondsUntilMidnight()
            redisTemplate.expire(key, secondsUntilMidnight, TimeUnit.SECONDS)
        }

        return count
    }

    fun canSend(email: String): Boolean {
        val key = getTodayKey(email)
        val count = redisTemplate.opsForValue().get(key)
        return (count?.toLongOrNull() ?: 0L) < MAX_SEND_COUNT
    }
}
