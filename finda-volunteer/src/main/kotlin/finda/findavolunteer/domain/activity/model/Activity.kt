package finda.findavolunteer.domain.activity.model

import finda.findavolunteer.domain.volunteer.model.Volunteer
import java.util.UUID

data class Activity(
    val id: UUID,
    val activityName: String,
    val volunteerId: UUID,
)
