package finda.findaauth.application.service.user

import finda.findaauth.global.security.principal.CustomUserDetails
import finda.security.passport.model.Passport
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserFacade {
    fun getCurrentUserId(): UUID {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw AuthenticationCredentialsNotFoundException("인증 정보가 존재하지 않습니다")

        return when (val principal = authentication.principal) {
            is Passport -> principal.userId
            is CustomUserDetails -> UUID.fromString(principal.username)
            else -> throw AuthenticationCredentialsNotFoundException("유효하지 않은 인증 principal 타입입니다: ${principal::class.simpleName}")
        }
    }
}
