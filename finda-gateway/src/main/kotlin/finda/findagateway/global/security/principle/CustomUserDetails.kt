package finda.findagateway.global.security.principle

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

/**
 * 인증이 필요한 요청 시 사용되는 커스텀 UserDetails 구현체
 *
 * Gateway에서는 JWT 등으로 이미 인증된 사용자의 정보만 필요하기 때문에
 * DB 접근 없이 사용자 정보와 권한만 보관함 -> 즉 CustomUserDetailsService가 필요하지 않음
 */
class CustomUserDetails(
    val userId: UUID,
    private val username: String,
    private val password: String,
    isStudent: Boolean,
    isTeacher: Boolean
) : UserDetails {

    private val authorities: List<GrantedAuthority> = when {
        isStudent && isTeacher -> listOf(
            SimpleGrantedAuthority("ROLE_STUDENT"),
            SimpleGrantedAuthority("ROLE_TEACHER")
        )
        isStudent -> listOf(SimpleGrantedAuthority("ROLE_STUDENT"))
        isTeacher -> listOf(SimpleGrantedAuthority("ROLE_TEACHER"))
        else -> emptyList()
    }

    override fun getAuthorities(): Collection<GrantedAuthority> = authorities
    override fun getPassword(): String = password
    override fun getUsername(): String = username
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true

    /**
     * Gateway에서는 JWT 검증만 수행하고 DB 조회를 하지 않으므로
     * 사용자의 탈퇴 여부를 확인할 수 없음
     * 실제 사용자 상태 확인은 Auth 서비스에서 수행
     */
    override fun isEnabled(): Boolean = true
}
