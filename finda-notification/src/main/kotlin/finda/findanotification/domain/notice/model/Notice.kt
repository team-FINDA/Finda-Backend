package finda.findanotification.domain.notice.model

import java.util.UUID

data class Notice(
    val id: UUID,
    val title: String,
    val body: String,
    val adminId: String
)
