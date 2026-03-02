package finda.findanotification.adapter.out.persistence.notificationpreference.entity

import finda.findanotification.adapter.out.persistence.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "tbl_volunteer_notification_preference")
class VolunteerNotificationPreferenceJpaEntity(
    id: UUID?,

    @Column(name = "volunteer_id", nullable = false)
    val volunteerId: String,

    @Column(name = "user_id", nullable = false)
    val userId: UUID,

    @Column(name = "enabled", nullable = false)
    val enabled: Boolean
) : BaseEntity()
