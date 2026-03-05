package finda.findavolunteer.domain.particitation.model

import java.util.UUID

data class VolunteerTeachers(
    val id: UUID = UUID(0, 0),
    val volunteerId: UUID,
    val userId: UUID
)
