package finda.findanotification.adapter.out.persistence.notificationpreference.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "tbl_volunteer_notification_preference")
class VolunteerNotificationPreferenceJpaEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: String,

    @Column(name = "volunteer_id", nullable = false)
    val volunteerId: String,

    @Column(name = "user_id", nullable = false)
    val userId: String,

    @Column(name = "enabled", nullable = false)
    val enabled: Boolean
)
