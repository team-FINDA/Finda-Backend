package finda.findanotification.adapter.out.persistence.notification.entity

import finda.findanotification.adapter.out.persistence.BaseEntity
import finda.findanotification.domain.notification.enum.NotificationType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "tbl_notification")
class NotificationJpaEntity(
    id: UUID?,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val body: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: NotificationType,

    @Column(nullable = true)
    val volunteerId: String
) : BaseEntity(id)
