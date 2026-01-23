package finda.findanotification.adapter.out.persistence.notificationpreference.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "tbl_volunteer_notification_preference")
class VolunteerNotificationPreferenceJpaEntity(
    @Id
    @Column(nullable = false)
    val id: String,

    @Column(nullable = false)
    val volunteerId: String,

    @Column(nullable = false)
    val userId: String,

    @Column(nullable = false)
    val enabled: Boolean
)
