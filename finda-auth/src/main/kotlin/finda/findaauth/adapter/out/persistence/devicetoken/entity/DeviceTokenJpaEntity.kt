package finda.findaauth.adapter.out.persistence.devicetoken.entity

import finda.findaauth.adapter.out.persistence.BaseEntity
import finda.findaauth.adapter.out.persistence.user.entity.UserJpaEntity
import finda.findaauth.domain.devicetoken.enum.DeviceOs
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "tbl_device_token")
class DeviceTokenJpaEntity(
    id: UUID?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: UserJpaEntity?,

    @Column(name = "device_token", nullable = false)
    val deviceToken: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "os", nullable = false)
    @Enumerated(EnumType.STRING)
    val os: DeviceOs
) : BaseEntity(id)
