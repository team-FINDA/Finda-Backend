package finda.findavolunteer.adapter.out.persistence.volunteer.entity.recurrence

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
@Table(name = "activity_recurrence_month")
class ActivityRecurrenceMonthJpaEntity(
    id: UUID?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id", nullable = false)
    val volunteer: VolunteerJpaEntity?,

    @Column(nullable = false)
    val day: Int
) : BaseEntity(id)
