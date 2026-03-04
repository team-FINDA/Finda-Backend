package finda.findanotification.application.port.`in`.notice

import finda.findanotification.application.port.`in`.notice.dto.request.CreateNoticeCommand

interface CreateNoticeUseCase {
    fun execute(request: CreateNoticeCommand)
}
