package finda.findavolunteer.domain.volunteer.model.recurrence

import java.util.UUID

data class ActivityRecurrenceMonth(
    val id: UUID = UUID(0, 0),
    val volunteerId: UUID,
    val day: Int
)
