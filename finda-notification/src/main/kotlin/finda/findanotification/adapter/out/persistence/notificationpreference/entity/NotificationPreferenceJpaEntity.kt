package finda.findanotification.adapter.out.persistence.notificationpreference.entity

import finda.findanotification.adapter.out.persistence.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.util.UUID

// 알림 활성화/비활성화 상태 저장 테이블
@Entity
@Table(name = "tbl_notification_preference")
class NotificationPreferenceJpaEntity(
    id: UUID?,

    @Column(nullable = false)
    val type: String,

    @Column(nullable = false)
    val userId: String,

    @Column(nullable = false)
    val enabled: Boolean
) : BaseEntity(id)
