package finda.findabatch.infra.event.dto.volunteer

import java.util.UUID

data class VolunteerRemindFiredEvent(
    val volunteerId: UUID
)
