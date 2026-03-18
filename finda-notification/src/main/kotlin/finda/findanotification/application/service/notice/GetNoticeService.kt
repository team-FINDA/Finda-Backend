package finda.findanotification.application.service.notice

import finda.findanotification.adapter.out.grpc.UserGrpcClient
import finda.findanotification.application.exception.notice.NoticeNotFoundException
import finda.findanotification.application.port.`in`.notice.GetNoticeUseCase
import finda.findanotification.application.port.`in`.notice.dto.response.GetNoticeResult
import finda.findanotification.application.port.out.notice.GetNoticePort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GetNoticeService(
    private val getNoticePort: GetNoticePort,
    private val userGrpcClient: UserGrpcClient
) : GetNoticeUseCase {

    override fun execute(id: UUID): GetNoticeResult {
        val notice = getNoticePort.findById(id)
            ?: throw NoticeNotFoundException

        val userName = userGrpcClient.getUserName(notice.userId) ?: "Unknown User"

        return GetNoticeResult(
            userName = userName,
            title = notice.title,
            body = notice.body,
            noticeDate = notice.noticeDate,
            noticeTime = notice.noticeTime
        )
    }
}
