package finda.findanotification.application.port.out.notice

import finda.findanotification.domain.notice.model.Notice

interface DeleteNoticePort {
    fun delete(notice: Notice)
}
