package finda.findanotification.application.port.out.notificationpreference

import finda.findanotification.domain.notificationpreference.model.NotificationPreference
import finda.findanotification.domain.notificationpreference.model.VolunteerNotificationPreference
import java.util.UUID

interface NotificationPreferenceQueryPort {
    fun findAllEnabled(): List<NotificationPreference>
    fun findByVolunteerId(volunteerId: UUID): VolunteerNotificationPreference?
}
