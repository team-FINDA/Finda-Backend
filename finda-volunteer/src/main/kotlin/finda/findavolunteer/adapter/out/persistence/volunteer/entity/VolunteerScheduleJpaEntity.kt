package finda.findavolunteer.adapter.out.persistence.volunteer.entity

import finda.findavolunteer.adapter.out.persistence.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "tbl_volunteer_schedule")
class VolunteerScheduleJpaEntity(
    id: UUID?,

    @Column(name = "start_date", nullable = false)
    val startDate: LocalDate,

    @Column(name = "end_date", nullable = false)
    val endDate: LocalDate,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id")
    val volunteer: VolunteerJpaEntity?
) : BaseEntity(id)
