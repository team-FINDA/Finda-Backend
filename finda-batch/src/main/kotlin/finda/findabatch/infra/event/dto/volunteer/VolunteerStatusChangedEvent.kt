package finda.findabatch.infra.event.dto.volunteer

import java.time.LocalDate
import java.util.UUID

data class VolunteerStatusChangedEvent(
    val volunteerId: UUID,
    val title: String,
    val applicationStartDate: LocalDate,
    val applicationEndDate: LocalDate,
    val workStartDate: LocalDate,
    val workEndDate: LocalDate
)
