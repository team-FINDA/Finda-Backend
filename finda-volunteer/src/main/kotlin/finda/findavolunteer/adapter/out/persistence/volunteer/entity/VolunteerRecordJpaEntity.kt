package finda.findavolunteer.adapter.out.persistence.volunteer.entity

import finda.findavolunteer.adapter.out.persistence.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "tbl_volunteer_record")
class VolunteerRecordJpaEntity(
    id: UUID?,

    @Column(nullable = false)
    val userId: String,

    @Column(nullable = false)
    val time: Int,

    @Column(nullable = false)
    val title: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id", nullable = false)
    val volunteer: VolunteerJpaEntity?
) : BaseEntity(id)
