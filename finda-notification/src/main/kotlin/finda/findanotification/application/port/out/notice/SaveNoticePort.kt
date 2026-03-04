package finda.findanotification.application.port.out.notice

import finda.findanotification.domain.notice.model.Notice

interface SaveNoticePort {
    fun save(notice: Notice): Notice
}
