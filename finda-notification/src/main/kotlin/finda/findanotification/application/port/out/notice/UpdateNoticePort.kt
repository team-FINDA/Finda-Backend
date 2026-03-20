package finda.findanotification.application.port.out.notice

import finda.findanotification.domain.notice.model.Notice
import finda.findanotification.domain.notice.type.Status
import java.util.UUID

interface UpdateNoticePort {
    fun update(notice: Notice): Notice
    fun updateStatus(noticeId: UUID, status: Status)
}
