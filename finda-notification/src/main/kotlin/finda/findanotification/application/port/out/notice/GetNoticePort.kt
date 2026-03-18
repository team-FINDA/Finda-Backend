package finda.findanotification.application.port.out.notice

import finda.findanotification.domain.notice.model.Notice
import java.util.UUID

interface GetNoticePort {
    fun findAll(): List<Notice>
    fun findById(id: UUID): Notice?
}
