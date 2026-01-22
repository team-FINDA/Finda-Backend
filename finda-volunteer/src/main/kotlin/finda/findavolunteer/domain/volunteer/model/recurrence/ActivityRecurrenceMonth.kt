package finda.findavolunteer.domain.volunteer.model.recurrence

import java.util.UUID

data class ActivityRecurrenceMonth(
    val id: UUID,
    val volunteerId: UUID,
    val day: Int
)
