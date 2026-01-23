package finda.findanotification.domain.notification.model

import finda.findanotification.domain.notification.enum.NotificationType
import java.util.UUID

data class Notification(
    val id: UUID,
    val title: String,
    val body: String,
    val type: NotificationType,
    val volunteerId: String
)
