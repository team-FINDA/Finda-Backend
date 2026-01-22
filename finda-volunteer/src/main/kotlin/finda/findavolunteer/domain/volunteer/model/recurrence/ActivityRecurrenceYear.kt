package finda.findavolunteer.domain.volunteer.model.recurrence

import java.util.UUID

data class ActivityRecurrenceYear(
    val id: UUID,
    val volunteerId: UUID,
    val day: Int,
    val month: Int,
)
