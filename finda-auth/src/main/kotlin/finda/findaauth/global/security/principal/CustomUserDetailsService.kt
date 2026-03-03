package finda.findaauth.global.security.principal

import finda.findaauth.application.exception.user.UserNotFoundException
import finda.findaauth.application.port.out.user.UserQueryPort
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userQueryPort: UserQueryPort
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userQueryPort.findByEmail(email)
            ?: throw UserNotFoundException

        return CustomUserDetails(
            user = user,
            username = user.name,
            authority = user.authority
        )
    }
}
