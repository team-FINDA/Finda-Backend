package finda.findavolunteer.adapter.out.persistence.qrcode.entity

import finda.findavolunteer.adapter.out.persistence.BaseEntity
import finda.findavolunteer.adapter.out.persistence.volunteer.entity.VolunteerJpaEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "tbl_qr_code")
class QrCodeJpaEntity(
    id: UUID?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id", nullable = true)
    val volunteer: VolunteerJpaEntity?,

    @Column(name = "code", nullable = false)
    val code: String,

    @Column(name = "generated_at", nullable = false)
    val generatedAt: LocalDateTime,

    @Column(name = "is_used", nullable = false)
    val isUsed: Boolean = false,

    @Column(name = "used_at", nullable = true)
    val usedAt: LocalDateTime? = null,

    @Column(name = "student_id", nullable = false)
    val studentId: UUID,

    @Column(name = "teacher_id", nullable = false)
    val teacherId: UUID
) : BaseEntity(id)
