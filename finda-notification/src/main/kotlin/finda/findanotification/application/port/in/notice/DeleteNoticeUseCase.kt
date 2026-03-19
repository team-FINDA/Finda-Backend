package finda.findanotification.application.port.`in`.notice

import finda.findanotification.application.port.`in`.notice.dto.request.DeleteNoticeCommand

interface DeleteNoticeUseCase {
    fun execute(deleteNoticeCommand: DeleteNoticeCommand)
}
