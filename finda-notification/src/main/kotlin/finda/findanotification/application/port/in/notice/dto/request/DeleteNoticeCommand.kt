package finda.findanotification.application.port.`in`.notice.dto.request

import java.util.UUID

data class DeleteNoticeCommand(
    val noticeId: UUID,
    val userId: UUID,
)
