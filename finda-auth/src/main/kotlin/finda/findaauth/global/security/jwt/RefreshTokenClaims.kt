package finda.findaauth.global.security.jwt

import finda.findaauth.domain.user.model.UserType
import java.util.UUID

/**
 * Refresh Token 검증 후 추출한 클레임을 반환하는 DTO
 *
 * Refresh Token에 userType 포함한 후 2개의 필드를 함께 반환하기 위해
 * Pair 대신 가독성을 위해 별도의 클래스로 분리
 */
data class RefreshTokenClaims(
    val userId: UUID,
    val userType: UserType
)
