package finda.findanotification.application.service.notice

import finda.findanotification.application.exception.notice.ForbiddenNoticeException
import finda.findanotification.application.exception.notice.NoticeNotFoundException
import finda.findanotification.application.port.`in`.notice.DeleteNoticeUseCase
import finda.findanotification.application.port.`in`.notice.dto.request.DeleteNoticeCommand
import finda.findanotification.application.port.out.notice.DeleteNoticePort
import finda.findanotification.application.port.out.notice.GetNoticePort
import org.springframework.stereotype.Service

@Service
class DeleteNoticeService(
    private val deleteNoticePort: DeleteNoticePort,
    private val getNoticePort: GetNoticePort
) : DeleteNoticeUseCase {
    override fun execute(command: DeleteNoticeCommand) {
        val notice = getNoticePort.findById(command.noticeId)
            ?: throw NoticeNotFoundException

        if (notice.userId != command.userId) {
            throw ForbiddenNoticeException
        }

        deleteNoticePort.delete(notice)
    }
}
