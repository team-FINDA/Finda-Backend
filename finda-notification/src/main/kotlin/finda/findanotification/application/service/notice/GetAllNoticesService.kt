package finda.findanotification.application.service.notice

import finda.findanotification.application.port.`in`.notice.GetAllNoticesUseCase
import finda.findanotification.application.port.`in`.notice.dto.response.GetAllNoticesResult
import finda.findanotification.application.port.out.notice.GetNoticePort
import org.springframework.stereotype.Service

@Service
class GetAllNoticesService(
    private val getNoticePort: GetNoticePort
) : GetAllNoticesUseCase {
    override fun execute(): List<GetAllNoticesResult> {
        return getNoticePort.findAll()
            .map { GetAllNoticesResult.from(it) }
    }
}
