package finda.findanotification.application.port.out.kafka

import java.util.UUID

interface CancelNoticeScheduledEventPort {
    fun cancel(noticeId: UUID)
}
