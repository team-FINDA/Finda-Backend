package finda.findavolunteer.application.facade

import finda.security.passport.model.Passport
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserFacade {
    fun currentUserId(): UUID {
        val passport = SecurityContextHolder.getContext().authentication.principal as Passport
        return passport.userId
    }
}
