package finda.findaauth.global.security.principle

import finda.findaauth.adapter.out.persistence.user.entity.UserJpaEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    val user: UserJpaEntity,
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
    override fun isEnabled(): Boolean = true
}
