package finda.findavolunteer.application.port.`in`.activity.dto.request

import java.util.UUID

data class CreateUserActivityCommand(
    val userActivityCommandList: List<UserActivityCommand>
)

data class UserActivityCommand(
    val userId: UUID,
    val activityId: UUID
)
