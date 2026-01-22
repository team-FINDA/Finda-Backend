package finda.findavolunteer.domain.volunteer.model

import finda.findavolunteer.domain.volunteer.enum.CycleType
import finda.findavolunteer.domain.volunteer.enum.VolunteerStatus
import java.time.LocalDate
import java.util.UUID

data class Volunteer(
    val id: UUID,
    val status: VolunteerStatus,
    val personnel: Int,
    val title: String,
    val unitVolunteerHours: Float,
    val applicationStartDate: LocalDate,
    val applicationEndDate: LocalDate,
    val workStartDate: LocalDate,
    val workEndDate: LocalDate,
    val cycleType: CycleType?,
    val userId: String,
)
