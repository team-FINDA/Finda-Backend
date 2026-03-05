package finda.security.jwt

import io.jsonwebtoken.Claims
import java.util.UUID

data class JwtClaims(
    val userId: UUID,
    val userType: String,
    val claims: Claims
) {
    fun <T> getClaim(key: String, type: Class<T>): T? {
        return claims.get(key, type)
    }
}
