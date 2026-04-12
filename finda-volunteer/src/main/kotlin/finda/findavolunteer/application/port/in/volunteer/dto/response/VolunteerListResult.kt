package finda.findavolunteer.application.port.`in`.volunteer.dto.response

import java.time.LocalDate
import java.util.UUID

data class VolunteerListResult(
    val volunteerId: UUID,
    val title: String,
    val workStartDate: LocalDate,
    val workEndDate: LocalDate,
    val unitVolunteerHours: Float
)
