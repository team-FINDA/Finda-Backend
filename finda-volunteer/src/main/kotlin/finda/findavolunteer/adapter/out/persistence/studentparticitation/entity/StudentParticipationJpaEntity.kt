package finda.findavolunteer.adapter.out.persistence.studentparticitation.entity

import finda.findavolunteer.adapter.out.persistence.BaseEntity
import finda.findavolunteer.adapter.out.persistence.volunteer.entity.VolunteerJpaEntity
import finda.findavolunteer.domain.particitation.enum.ParticitationStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "tbl_student_particitation")
class StudentParticipationJpaEntity(
    id: UUID?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id")
    val volunteer: VolunteerJpaEntity?,

    @Column(name = "status", nullable = false)
    val status: ParticitationStatus,

    @Column(name = "participated_at", nullable = false)
    val participatedAt: LocalDateTime,

    @Column(name = "user_id", nullable = false)
    val userId: String
) : BaseEntity(id)
