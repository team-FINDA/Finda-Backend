package finda.findaauth.adapter.out.persistence.auth.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(value = "teacher_signup_token", timeToLive = 600)
class TeacherSignupToken(

    @Id
    val token: String
)
