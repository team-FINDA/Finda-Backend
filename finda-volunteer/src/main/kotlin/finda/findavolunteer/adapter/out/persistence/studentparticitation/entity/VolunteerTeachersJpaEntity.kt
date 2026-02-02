package finda.findavolunteer.adapter.out.persistence.studentparticitation.entity

import finda.findavolunteer.adapter.out.persistence.BaseEntity
import finda.findavolunteer.adapter.out.persistence.volunteer.entity.VolunteerJpaEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "tbl_volunteer_teachers")
class VolunteerTeachersJpaEntity(
    id: UUID?,

    @Column(name = "user_id", nullable = false)
    val userId: UUID,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id")
    val volunteer: VolunteerJpaEntity?
) : BaseEntity(id)
