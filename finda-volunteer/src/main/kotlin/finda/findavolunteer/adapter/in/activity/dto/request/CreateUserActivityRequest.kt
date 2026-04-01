package finda.findavolunteer.adapter.`in`.activity.dto.request

import java.util.*

data class CreateUserActivityRequest(
    val userActivityList: List<UserActivity>
)
data class UserActivity(
    val userId: UUID,
    val activityId: UUID
)
