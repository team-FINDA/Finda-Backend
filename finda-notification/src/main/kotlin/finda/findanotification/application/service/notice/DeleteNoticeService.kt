package finda.findanotification.application.service.notice

import finda.findanotification.application.exception.notice.ForbiddenNoticeException
import finda.findanotification.application.exception.notice.NoticeNotFoundException
import finda.findanotification.application.port.`in`.notice.DeleteNoticeUseCase
import finda.findanotification.application.port.out.notice.DeleteNoticePort
import finda.findanotification.application.port.out.notice.GetNoticePort
import finda.security.passport.model.Passport
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class DeleteNoticeService(
    private val deleteNoticePort: DeleteNoticePort,
    private val getNoticePort: GetNoticePort
) : DeleteNoticeUseCase {
    override fun execute(id: UUID, passport: Passport) {
        val notice = getNoticePort.findById(id)
            ?: throw NoticeNotFoundException

        if (notice.userId != passport.userId) {
            throw ForbiddenNoticeException
        }
        deleteNoticePort.delete(notice)
    }
}
