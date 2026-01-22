package finda.findavolunteer.adapter.out.persistence.activity.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.UUID
import finda.findavolunteer.adapter.out.persistence.BaseEntity

@Entity
@Table(name = "tbl_user_activity")
class UserActivityJpaEntity(
    id: UUID?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id", nullable = false)
    val activity: ActivityJpaEntity?,
) : BaseEntity(id)
