package finda.findanotification.application.port.`in`.notice

import finda.findanotification.application.port.`in`.notice.dto.request.NoticeCommand
import finda.security.passport.model.Passport

interface CreateNoticeUseCase {
    fun execute(request: NoticeCommand, passport: Passport)
}
