package finda.findavolunteer.adapter.`in`.activity.dto.request

import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import java.util.*

data class CreateUserActivityRequest(
    @field:Valid
    @field:NotEmpty
    val userActivityList: List<UserActivity>
)

data class UserActivity(
    val userId: UUID,
    val activityId: UUID
)
