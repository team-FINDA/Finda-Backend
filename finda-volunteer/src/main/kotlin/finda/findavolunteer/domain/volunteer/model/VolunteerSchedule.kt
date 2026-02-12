package finda.findavolunteer.domain.volunteer.model

import java.time.LocalDate
import java.util.UUID

data class VolunteerSchedule(
    val id: UUID,
    val date: LocalDate,
    val volunteerId: UUID
)
