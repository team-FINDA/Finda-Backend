package finda.findanotification.application.service.notice

import finda.findanotification.adapter.out.grpc.UserGrpcClient
import finda.findanotification.application.exception.notice.NoticeNotFoundException
import finda.findanotification.application.port.`in`.notice.UpdateNoticeUseCase
import finda.findanotification.application.port.`in`.notice.dto.request.NoticeCommand
import finda.findanotification.application.port.`in`.notice.dto.response.NoticeResult
import finda.findanotification.application.port.out.kafka.SendNoticeScheduledEventPort
import finda.findanotification.application.port.out.kafka.CancelNoticeScheduledEventPort
import finda.findanotification.application.port.out.notice.GetNoticePort
import finda.findanotification.application.port.out.notice.UpdateNoticePort
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class UpdateNoticeService(
    private val getNoticePort: GetNoticePort,
    private val updateNoticePort: UpdateNoticePort,
    private val userGrpcClient: UserGrpcClient,
    private val cancelNoticeScheduledEventPort: CancelNoticeScheduledEventPort,
    private val sendNoticeScheduledEventPort: SendNoticeScheduledEventPort
) : UpdateNoticeUseCase {

    override fun execute(id: UUID, command: NoticeCommand): NoticeResult {
        val notice = getNoticePort.findById(id)
            ?: throw NoticeNotFoundException

        val scheduledAt = LocalDateTime.of(command.noticeDate, command.noticeTime)
        require(scheduledAt.isAfter(LocalDateTime.now())) {
            "예약 시간은 현재 시간 이후여야 합니다."
        }

        cancelNoticeScheduledEventPort.cancel(notice.id)

        notice.update(
            title = command.title,
            body = command.body,
            noticeDate = command.noticeDate,
            noticeTime = command.noticeTime
        )
        updateNoticePort.update(notice)

        sendNoticeScheduledEventPort.send(notice)

        val userName = userGrpcClient.getUserName(notice.userId) ?: "Unknown User"

        return NoticeResult(
            id = notice.id,
            userName = userName,
            title = notice.title,
            body = notice.body,
            noticeDate = notice.noticeDate,
            noticeTime = notice.noticeTime
        )
    }
}
