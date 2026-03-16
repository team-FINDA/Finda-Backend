package finda.findanotification.application.service.notice

import finda.findanotification.application.port.`in`.notice.DeleteNoticeUseCase
import finda.findanotification.application.port.out.notice.DeleteNoticePort
import java.util.UUID

class DeleteNoticeService(
    private val deleteNoticePort : DeleteNoticePort,
) : DeleteNoticeUseCase {
    override fun execute(id: UUID) {
        deleteNoticePort.delete(id)
    }
}
