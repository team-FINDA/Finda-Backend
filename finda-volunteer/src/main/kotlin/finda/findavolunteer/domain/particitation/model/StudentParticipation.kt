package finda.findavolunteer.domain.particitation.model

import finda.findavolunteer.domain.particitation.enum.ParticitationStatus
import java.time.LocalDateTime
import java.util.UUID

data class StudentParticipation(
    val id: UUID,
    val volunteerId: UUID,
    val status: ParticitationStatus,
    val participatedAt: LocalDateTime,
    val userId: String
)
