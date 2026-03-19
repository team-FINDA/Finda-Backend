package finda.findanotification.application.service.user.facade

import finda.security.passport.model.Passport
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserFacade {
    fun getCurrentPassport(): Passport {
        val principal = SecurityContextHolder.getContext().authentication.principal
        return principal as Passport
    }

    fun getCurrentUserId(): UUID {
        return getCurrentPassport().userId
    }
}
