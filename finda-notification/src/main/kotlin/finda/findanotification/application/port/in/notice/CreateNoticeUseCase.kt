package finda.findanotification.application.port.`in`.notice

import finda.findanotification.application.port.`in`.notice.dto.request.NoticeCommand
import finda.findanotification.application.port.`in`.notice.dto.response.NoticeResult

interface CreateNoticeUseCase {
    fun execute(command: NoticeCommand) : NoticeResult
}
