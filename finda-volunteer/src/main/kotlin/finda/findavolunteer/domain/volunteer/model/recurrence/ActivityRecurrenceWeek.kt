package finda.findavolunteer.domain.volunteer.model.recurrence

import finda.findavolunteer.domain.volunteer.enum.Weekday
import java.util.UUID

data class ActivityRecurrenceWeek(
    val id: UUID = UUID(0, 0),
    val weekday: Weekday,
    val volunteerId: UUID
)
