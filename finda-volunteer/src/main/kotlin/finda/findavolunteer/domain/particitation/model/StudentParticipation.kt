package finda.findavolunteer.domain.particitation.model

import finda.findavolunteer.domain.particitation.enum.ParticitationStatus
import finda.findavolunteer.domain.volunteer.model.Volunteer
import java.time.LocalDateTime
import java.util.UUID

data class StudentParticipation(
    val id: UUID,
    val volunteerId: UUID,
    val status: ParticitationStatus,
    val participatedAt: LocalDateTime,
    val userId: String,
)
