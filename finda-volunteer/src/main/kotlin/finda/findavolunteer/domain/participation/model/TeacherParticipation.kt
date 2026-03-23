package finda.findavolunteer.domain.participation.model

import java.util.UUID

data class TeacherParticipation(
    val id: UUID = UUID(0, 0),
    val volunteerId: UUID,
    val userId: UUID
)
