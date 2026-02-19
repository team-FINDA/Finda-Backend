package finda.findabatch.infra.event.dto.volunteer

import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class
VolunteerRemindEvent(
    val volunteerId: UUID,
    val scheduleDate: List<LocalDate>,
    val remindTime: LocalTime
)
