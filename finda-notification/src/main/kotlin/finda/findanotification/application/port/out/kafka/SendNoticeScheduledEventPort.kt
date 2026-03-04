package finda.findanotification.application.port.out.kafka

import finda.findanotification.domain.notice.model.Notice

interface SendNoticeScheduledEventPort {
    fun send(notice: Notice)
}
