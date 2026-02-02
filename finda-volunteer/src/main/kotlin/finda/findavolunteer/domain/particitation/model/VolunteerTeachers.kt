package finda.findavolunteer.domain.particitation.model

import java.util.UUID

data class VolunteerTeachers(
    val id: UUID,
    val volunteerId: UUID,
    val userId: UUID
)
