package finda.findanotification.application.service.notice

import finda.findanotification.adapter.out.grpc.UserGrpcClient
import finda.findanotification.application.port.`in`.notice.CreateNoticeUseCase
import finda.findanotification.application.port.`in`.notice.dto.request.NoticeCommand
import finda.findanotification.application.port.`in`.notice.dto.response.NoticeResult
import finda.findanotification.application.port.out.notice.SaveNoticePort
import finda.findanotification.domain.notice.model.Notice
import finda.findanotification.domain.notice.type.Status
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CreateNoticeService(
    private val saveNoticePort: SaveNoticePort,
    private val userGrpcClient: UserGrpcClient
) : CreateNoticeUseCase {

    override fun execute(command: NoticeCommand): NoticeResult {
        val scheduledAt = LocalDateTime.of(command.noticeDate, command.noticeTime)
        require(scheduledAt.isAfter(LocalDateTime.now())) {
            "예약 시간은 현재 시간 이후여야 합니다."
        }

        val notice = Notice(
            title = command.title,
            body = command.body,
            userId = command.userId,
            status = Status.RECEIVED,
            noticeDate = command.noticeDate,
            noticeTime = command.noticeTime
        )

        val savedNotice = saveNoticePort.save(notice)

        val userName = userGrpcClient.getUserName(savedNotice.userId) ?: "Unknown User"

        return NoticeResult(
            id = savedNotice.id,
            userName = userName,
            title = savedNotice.title,
            body = savedNotice.body,
            noticeDate = savedNotice.noticeDate,
            noticeTime = savedNotice.noticeTime
        )
    }
}
