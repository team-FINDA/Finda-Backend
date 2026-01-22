package finda.findavolunteer.adapter.out.persistence.activity.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.UUID
import finda.findavolunteer.adapter.out.persistence.BaseEntity
import finda.findavolunteer.adapter.out.persistence.volunteer.entity.VolunteerJpaEntity

@Entity
@Table(name = "tbl_activity")
class ActivityJpaEntity(
    id: UUID?,

    @Column(nullable = false)
    val activityName: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id", nullable = false)
    val volunteer: VolunteerJpaEntity?,
) : BaseEntity(id)
