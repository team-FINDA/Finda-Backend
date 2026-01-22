package finda.findavolunteer.domain.volunteer.model.recurrence

import finda.findavolunteer.domain.volunteer.enum.Weekday
import finda.findavolunteer.domain.volunteer.model.Volunteer
import java.util.UUID

data class ActivityRecurrenceWeek(
    val id: UUID,
    val weekday: Weekday,
    val volunteerId: UUID
    )
