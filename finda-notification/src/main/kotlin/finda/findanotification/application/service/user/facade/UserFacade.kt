package finda.findanotification.application.service.user.facade

import finda.security.passport.model.Passport
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserFacade {
    fun getCurrentPassport(): Passport {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw AuthenticationCredentialsNotFoundException("인증 정보가 존재하지 않습니다")

        return authentication.principal as? Passport
            ?: throw AuthenticationCredentialsNotFoundException("유효하지 않은 인증 principal 타입입니다: ${authentication.principal::class.simpleName}")
    }

    fun getCurrentUserId(): UUID {
        return getCurrentPassport().userId
    }
}
