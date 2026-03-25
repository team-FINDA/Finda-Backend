package finda.findanotification.application.port.`in`.notice

import finda.findanotification.application.port.`in`.notice.dto.request.NoticeCommand
import finda.findanotification.application.port.`in`.notice.dto.response.NoticeResult
import java.util.UUID

interface UpdateNoticeUseCase {
    fun execute(id: UUID, command: NoticeCommand): NoticeResult
}
