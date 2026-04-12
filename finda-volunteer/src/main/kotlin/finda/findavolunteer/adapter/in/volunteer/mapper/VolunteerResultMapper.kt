package finda.findavolunteer.adapter.`in`.volunteer.mapper

import finda.findavolunteer.adapter.`in`.volunteer.dto.response.ActivityResponse
import finda.findavolunteer.adapter.`in`.volunteer.dto.response.StudentParticipationResponse
import finda.findavolunteer.adapter.`in`.volunteer.dto.response.UserActivityResponse
import finda.findavolunteer.adapter.`in`.volunteer.dto.response.VolunteerDetailResponse
import finda.findavolunteer.adapter.`in`.volunteer.dto.response.VolunteerListResponse
import finda.findavolunteer.adapter.`in`.volunteer.dto.response.VolunteerScheduleResponse
import finda.findavolunteer.application.port.`in`.volunteer.dto.response.VolunteerDetailResult
import finda.findavolunteer.application.port.`in`.volunteer.dto.response.VolunteerListResult

fun VolunteerDetailResult.toResponse(): VolunteerDetailResponse =
    VolunteerDetailResponse(
        volunteerId = volunteerId,
        title = title,
        description = description,
        status = status,
        personnel = personnel,
        unitVolunteerHours = unitVolunteerHours,
        applicationStartDate = applicationStartDate,
        applicationEndDate = applicationEndDate,
        workStartDate = workStartDate,
        workEndDate = workEndDate,
        cycleType = cycleType,
        weekdays = weekdayList,
        monthDate = monthDate,
        remindTime = remindTime,
        groupVolunteerType = groupVolunteerType,
        volunteerType = volunteerType,
        writerUserId = writerUserId,
        schedules = scheduleResultList.map {
            VolunteerScheduleResponse(
                scheduleId = it.scheduleId,
                scheduleDate = it.scheduleDate
            )
        },
        studentParticipations = studentParticipationResultList.map {
            StudentParticipationResponse(
                userId = it.userId,
                name = it.name,
                status = it.status,
                participatedAt = it.participatedAt
            )
        },
        activities = activityResultList.map {
            ActivityResponse(
                activityId = it.activityId,
                activityName = it.activityName
            )
        },
        userActivities = userActivityResultList.map {
            UserActivityResponse(
                userId = it.userId,
                userName = it.userName,
                activityId = it.activityId
            )
        }
    )

fun VolunteerListResult.toResponse(): VolunteerListResponse =
    VolunteerListResponse(
        volunteerId = volunteerId,
        title = title,
        workStartDate = workStartDate,
        workEndDate = workEndDate,
        unitVolunteerHours = unitVolunteerHours
    )
