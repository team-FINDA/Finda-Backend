package finda.findavolunteer.domain.activity.model

import java.util.UUID

data class Activity(
    val id: UUID = UUID(0, 0),
    val activityName: String,
    val volunteerId: UUID
)
