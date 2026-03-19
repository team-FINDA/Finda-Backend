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

    override fun execute(command: NoticeCommand) {

        val scheduledAt = LocalDateTime.of(command.noticeDate, command.noticeTime)
        require(scheduledAt.isAfter(LocalDateTime.now())) {
            "예약 시간은 현재 시간 이후여야 합니다."
        }

        val notice = Notice(
            id = UUID.randomUUID(),
            title = command.title,
            body = command.body,
            userId = command.userId,
            status = Status.RECEIVED,
            noticeDate = command.noticeDate,
            noticeTime = command.noticeTime
        )

        saveNoticePort.save(notice)

        sendNoticeScheduledEventPort.send(notice)
    }
}
