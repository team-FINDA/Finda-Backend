package finda.findanotification.application.service.notice

import finda.findanotification.application.port.`in`.notice.CreateNoticeUseCase
import finda.findanotification.application.port.`in`.notice.dto.request.NoticeCommand
import finda.findanotification.application.port.out.kafka.SendNoticeScheduledEventPort
import finda.findanotification.application.port.out.notice.SaveNoticePort
import finda.findanotification.domain.notice.model.Notice
import finda.findanotification.domain.notice.type.Status
import finda.security.passport.model.Passport
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CreateNoticeService(
    private val saveNoticePort: SaveNoticePort,
    private val sendNoticeScheduledEventPort: SendNoticeScheduledEventPort
) : CreateNoticeUseCase {

    override fun execute(request: NoticeCommand, passport: Passport) {
        val notice = Notice(
            id = UUID.randomUUID(),
            title = request.title,
            body = request.body,
            userId = passport.userId,
            status = Status.RECEIVED,
            noticeDate = request.noticeDate,
            noticeTime = request.noticeTime
        )

        saveNoticePort.save(notice)

        sendNoticeScheduledEventPort.send(notice)
    }
}
