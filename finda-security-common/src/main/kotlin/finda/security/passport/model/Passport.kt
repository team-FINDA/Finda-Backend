package finda.security.passport.model

import java.util.UUID

data class Passport(
    val userId: UUID,
    val authority: Authority,
    val userIntegrity: String,
    val issuedAt: Long, // 발급 시간 (Unix timestamp in milliseconds)
    val expiresAt: Long // 만료 시간 (Unix timestamp in milliseconds)
)
