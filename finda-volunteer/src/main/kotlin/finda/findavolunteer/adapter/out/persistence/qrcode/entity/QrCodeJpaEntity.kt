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
    @JoinColumn(name = "volunteer_id")
    val volunteer: VolunteerJpaEntity?,

    @Column(nullable = false)
    val code: String,

    @Column(nullable = false)
    val generatedAt: LocalDateTime,

    @Column(nullable = false)
    val isUsed: Boolean = false,

    @Column(nullable = false)
    val usedAt: LocalDateTime? = null,

    @Column(nullable = false)
    val studentId: UUID,

    @Column(nullable = false)
    val teacherId: UUID
) : BaseEntity(id)
