package finda.findanotification.application.service.notice

import finda.findanotification.application.exception.notice.NoticeNotFoundException
import finda.findanotification.application.port.`in`.notice.UpdateNoticeUseCase
import finda.findanotification.application.port.`in`.notice.dto.request.NoticeCommand
import finda.findanotification.application.port.out.notice.GetNoticePort
import finda.findanotification.application.port.out.notice.UpdateNoticePort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UpdateNoticeService(
    private val getNoticePort: GetNoticePort,
    private val updateNoticePort: UpdateNoticePort
) : UpdateNoticeUseCase {

    override fun execute(id: UUID, command: NoticeCommand): NoticeCommand {
        val notice = getNoticePort.findById(id)
            ?: throw NoticeNotFoundException

        notice.update(
            title = command.title,
            body = command.body,
            noticeDate = command.noticeDate,
            noticeTime = command.noticeTime
        )

        val updated = updateNoticePort.update(notice)

        return NoticeCommand.from(updated)
    }
}
