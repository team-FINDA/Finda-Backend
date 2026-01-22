package finda.findavolunteer.adapter.out.persistence.studentparticitation.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.UUID
import finda.findavolunteer.adapter.out.persistence.BaseEntity
import finda.findavolunteer.adapter.out.persistence.volunteer.entity.VolunteerJpaEntity
import finda.findavolunteer.domain.particitation.enum.ParticitationStatus
import java.time.LocalDateTime

@Entity
@Table(name = "tbl_student_particitation")
class StudentParticipationJpaEntity(
    id: UUID?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id", nullable = false)
    val volunteer: VolunteerJpaEntity?,

    @Column(nullable = false)
    val status: ParticitationStatus,

    @Column(nullable = false)
    val participatedAt: LocalDateTime,

    @Column(nullable = false)
    val userId: String,
) : BaseEntity(id)
