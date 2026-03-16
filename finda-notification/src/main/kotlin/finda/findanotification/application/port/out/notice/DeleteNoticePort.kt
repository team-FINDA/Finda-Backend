package finda.findanotification.application.port.out.notice

import java.util.UUID

interface DeleteNoticePort {
    fun delete(id: UUID)
}
