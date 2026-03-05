package finda.findavolunteer.domain.volunteer.model

import java.util.UUID

data class VolunteerRecord(
    val id: UUID = UUID(0, 0),
    val userId: String,
    val volunteerTime: Int,
    val title: String,
    val volunteerId: UUID
)
