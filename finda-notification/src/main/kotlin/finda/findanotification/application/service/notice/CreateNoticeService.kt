package finda.findanotification.application.service.notice

import finda.findanotification.application.port.`in`.notice.CreateNoticeUseCase
import finda.findanotification.application.port.`in`.notice.dto.request.NoticeCommand
import finda.findanotification.application.port.out.kafka.SendNoticeScheduledEventPort
import finda.findanotification.application.port.out.notice.SaveNoticePort
import finda.findanotification.domain.notice.model.Notice
import finda.findanotification.domain.notice.type.Status
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class CreateNoticeService(
    private val saveNoticePort: SaveNoticePort,
    private val sendNoticeScheduledEventPort: SendNoticeScheduledEventPort
) : CreateNoticeUseCase {

    override fun execute(noticeCommand: NoticeCommand) {

        val scheduledAt = LocalDateTime.of(noticeCommand.noticeDate, noticeCommand.noticeTime)
        require(scheduledAt.isAfter(LocalDateTime.now())) {
            "예약 시간은 현재 시간 이후여야 합니다."
        }

        val notice = Notice(
            id = UUID.randomUUID(),
            title = noticeCommand.title,
            body = noticeCommand.body,
            userId = noticeCommand.userId,
            status = Status.RECEIVED,
            noticeDate = noticeCommand.noticeDate,
            noticeTime = noticeCommand.noticeTime
        )

        saveNoticePort.save(notice)

        sendNoticeScheduledEventPort.send(notice)
    }
}
