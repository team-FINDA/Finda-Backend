package finda.findanotification.domain.notificationpreference.model

import java.util.UUID

data class VolunteerNotificationPreference(
    val id: UUID,
    val volunteerId: UUID,
    val userId: UUID,
    val enabled: Boolean
)
