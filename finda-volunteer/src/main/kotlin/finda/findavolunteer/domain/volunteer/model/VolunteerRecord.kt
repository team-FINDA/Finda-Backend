package finda.findavolunteer.domain.volunteer.model

import java.util.UUID

data class VolunteerRecord(
    val id: UUID,
    val userId: String,
    val time: Int,
    val title: String,
    val volunteerId: UUID,
)
