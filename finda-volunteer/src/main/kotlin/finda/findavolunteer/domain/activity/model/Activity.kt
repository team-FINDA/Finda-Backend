package finda.findavolunteer.domain.activity.model

import java.util.UUID

data class Activity(
    val id: UUID,
    val activityName: String,
    val volunteerId: UUID
)
