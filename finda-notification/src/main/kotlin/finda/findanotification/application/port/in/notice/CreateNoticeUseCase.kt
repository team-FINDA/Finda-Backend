package finda.findanotification.application.port.`in`.notice

import finda.findanotification.application.port.`in`.notice.dto.request.NoticeCommand

interface CreateNoticeUseCase {
    fun execute(command: NoticeCommand)
}
