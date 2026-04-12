package finda.findavolunteer.adapter.`in`.activity.mapper

import finda.findavolunteer.adapter.`in`.activity.dto.request.CreateUserActivityRequest
import finda.findavolunteer.application.port.`in`.activity.dto.request.CreateUserActivityCommand
import finda.findavolunteer.application.port.`in`.activity.dto.request.UserActivityCommand

fun CreateUserActivityRequest.toCommand(): CreateUserActivityCommand =
    CreateUserActivityCommand(
        userActivityCommandList = userActivityList.map {
            UserActivityCommand(
                userId = it.userId,
                activityId = it.activityId
            )
        }
    )
