package finda.findanotification.application.port.out.notice

import finda.findanotification.domain.notice.model.Notice

interface UpdateNoticePort {
    fun update(notice: Notice): Notice
}
