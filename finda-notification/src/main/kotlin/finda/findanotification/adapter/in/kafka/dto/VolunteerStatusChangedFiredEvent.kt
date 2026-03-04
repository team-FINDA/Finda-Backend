package finda.findanotification.adapter.`in`.kafka.dto

import finda.findanotification.domain.volunteer.VolunteerProgress
import finda.findanotification.domain.volunteer.VolunteerStatus
import java.util.UUID

data class VolunteerStatusChangedFiredEvent(
    val volunteerId: UUID,
    val status: VolunteerStatus,
    val progress: VolunteerProgress
)
