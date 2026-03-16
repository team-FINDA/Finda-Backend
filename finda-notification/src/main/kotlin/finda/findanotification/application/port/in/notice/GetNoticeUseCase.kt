package finda.findanotification.application.port.`in`.notice

import finda.findanotification.application.port.`in`.notice.dto.response.GetNoticeResult
import java.util.UUID

interface GetNoticeUseCase {
    fun execute(id: UUID): GetNoticeResult
}
