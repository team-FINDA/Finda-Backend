package finda.findanotification.application.service.notice

import finda.findanotification.application.port.`in`.notice.CreateNoticeUseCase
import finda.findanotification.application.port.`in`.notice.dto.request.CreateNoticeCommand
import finda.findanotification.application.port.out.kafka.SendNoticeScheduledEventPort
import finda.findanotification.application.port.out.notice.SaveNoticePort
import finda.findanotification.application.service.kafka.NoticeNotificationService
import finda.findanotification.domain.notice.model.Notice
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

@Service
class CreateNoticeService(
    private val saveNoticePort: SaveNoticePort,
    private val sendNoticeScheduledEventPort: SendNoticeScheduledEventPort,
    private val noticeNotificationService: NoticeNotificationService
) : CreateNoticeUseCase {

    override fun execute(request: CreateNoticeCommand) {
        val notice = Notice(
            id = UUID.randomUUID(),
            title = request.title,
            body = request.body,
            noticeDate = request.noticeDate ?: LocalDate.now(),
            noticeTime = request.noticeTime ?: LocalTime.now()
        )

        saveNoticePort.save(notice)

        if (request.noticeDate == null && request.noticeTime == null) {
            noticeNotificationService.sendImmediate(notice)
        } else {
            sendNoticeScheduledEventPort.send(notice)
        }
    }
}
