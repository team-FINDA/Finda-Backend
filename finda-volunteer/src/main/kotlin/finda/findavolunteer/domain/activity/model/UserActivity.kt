package finda.findavolunteer.domain.activity.model

import java.util.UUID

data class UserActivity(
    val id: UUID = UUID(0, 0),
    val activityId: UUID,
    val userId: UUID
)
