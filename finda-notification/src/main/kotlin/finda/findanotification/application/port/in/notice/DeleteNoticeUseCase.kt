package finda.findanotification.application.port.`in`.notice

import finda.security.passport.model.Passport
import java.util.UUID

interface DeleteNoticeUseCase {
    fun execute(id: UUID, passport: Passport)
}
