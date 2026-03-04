package finda.findaauth.global.security.principal

import finda.findaauth.domain.user.model.User
import finda.security.passport.model.Authority
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime

class CustomUserDetails(
    val user: User,
    private val username: String,
    private val authority: Authority,
    private val deletedAt: LocalDateTime? = null
) : UserDetails {
    private val authorities: List<GrantedAuthority> = when (authority) {
        Authority.TEACHER -> listOf(SimpleGrantedAuthority("ROLE_TEACHER"))
        Authority.STUDENT -> listOf(SimpleGrantedAuthority("ROLE_STUDENT"))
        else -> emptyList()
    }

    override fun getAuthorities(): Collection<GrantedAuthority> = authorities
    override fun getPassword(): String? = null
    override fun getUsername(): String = username
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = deletedAt == null
}
