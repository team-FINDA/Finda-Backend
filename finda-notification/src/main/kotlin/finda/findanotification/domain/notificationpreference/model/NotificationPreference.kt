package finda.findanotification.domain.notificationpreference.model

import java.util.UUID

data class NotificationPreference(
    val id: UUID = UUID(0, 0),
    val type: String,
    val userId: String,
    val enabled: Boolean
)
