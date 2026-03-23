package finda.findavolunteer.domain.participation.model

import finda.findavolunteer.domain.participation.enum.ParticitationStatus
import java.time.LocalDateTime
import java.util.UUID

data class StudentParticipation(
    val id: UUID = UUID(0, 0),
    val volunteerId: UUID,
    val status: ParticitationStatus,
    val participatedAt: LocalDateTime,
    val userId: UUID
)
