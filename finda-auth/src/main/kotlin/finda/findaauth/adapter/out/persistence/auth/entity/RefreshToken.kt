package finda.findaauth.adapter.out.persistence.auth.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed
import java.util.UUID

/**
 * 비즈니스 로직이 없는 단순 토큰 저장소이므로
 * 도메인 모델/Mapper를 구현하지 않음
 */
@RedisHash
class RefreshToken(

    @Id
    val token: String,

    @Indexed
    val userId: UUID,

    @TimeToLive
    val ttl: Long
)
