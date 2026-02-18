package finda.findabatch.infra.event.dto.volunteer

import finda.findabatch.domain.volunteer.VolunteerProgress
import finda.findabatch.domain.volunteer.VolunteerStatus
import java.util.UUID

data class VolunteerStatusChangedFiredEvent(
    val volunteerId: UUID,
    val status: VolunteerStatus,
    val progress: VolunteerProgress
)
