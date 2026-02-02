package finda.findanotification.domain.notificationpreference.model

data class VolunteerNotificationPreference(
    val id: String,
    val volunteerId: String,
    val userId: String,
    val enabled: Boolean
)
