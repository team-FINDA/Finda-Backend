package finda.findanotification.domain.notificationpreference.model

import java.util.UUID

data class VolunteerNotificationPreference(
    val id: UUID,
    val volunteerId: String,
    val userId: UUID,
    val enabled: Boolean
)
