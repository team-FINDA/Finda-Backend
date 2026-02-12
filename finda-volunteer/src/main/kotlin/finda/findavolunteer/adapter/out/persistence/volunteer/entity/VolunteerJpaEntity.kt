package finda.findavolunteer.adapter.out.persistence.volunteer.entity

import finda.findavolunteer.adapter.out.persistence.BaseEntity
import finda.findavolunteer.domain.volunteer.enum.CycleType
import finda.findavolunteer.domain.volunteer.enum.VolunteerStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

@Entity
@Table(name = "tbl_volunteer")
class VolunteerJpaEntity(
    id: UUID?,

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    val status: VolunteerStatus,

    @Column(name = "personnel", nullable = false)
    val personnel: Int,

    @Column(name = "title", nullable = false)
    val title: String,

    @Column(name = "unit_volunteer_hours", nullable = false)
    val unitVolunteerHours: Float,

    @Column(name = "application_start_date", nullable = false)
    val applicationStartDate: LocalDate,

    @Column(name = "application_end_date", nullable = false)
    val applicationEndDate: LocalDate,

    @Column(name = "work_start_date", nullable = false)
    val workStartDate: LocalDate,

    @Column(name = "work_end_date", nullable = false)
    val workEndDate: LocalDate,

    @Column(name = "cycle_type", nullable = true)
    @Enumerated(EnumType.STRING)
    val cycleType: CycleType?,

    @Column(name = "user_id", nullable = false)
    val userId: String,

    @Column(name = "remind_time", nullable = false)
    val remindTime: LocalTime
) : BaseEntity(id)
