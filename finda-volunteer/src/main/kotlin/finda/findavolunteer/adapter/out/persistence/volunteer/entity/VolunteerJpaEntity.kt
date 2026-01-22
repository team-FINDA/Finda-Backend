package finda.findavolunteer.adapter.out.persistence.volunteer.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDate
import java.util.UUID
import finda.findavolunteer.adapter.out.persistence.BaseEntity
import finda.findavolunteer.domain.volunteer.enum.CycleType
import finda.findavolunteer.domain.volunteer.enum.VolunteerStatus

@Entity
@Table(name = "tbl_volunteer")
class VolunteerJpaEntity(
    id: UUID?,

    @Column(nullable = false)
    val status: VolunteerStatus,

    @Column(nullable = false)
    val personnel: Int,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val unitVolunteerHours: Float,

    @Column(nullable = false)
    val applicationStartDate: LocalDate,

    @Column(nullable = false)
    val applicationEndDate: LocalDate,

    @Column(nullable = false)
    val workStartDate: LocalDate,

    @Column(nullable = false)
    val workEndDate: LocalDate,

    @Column(nullable = false)
    val cycleType: CycleType?,

    @Column(nullable = false)
    val userId: String,
) : BaseEntity(id)
