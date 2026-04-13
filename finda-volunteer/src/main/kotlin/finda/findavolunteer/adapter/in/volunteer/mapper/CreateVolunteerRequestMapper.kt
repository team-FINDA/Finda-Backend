package finda.findavolunteer.adapter.`in`.volunteer.mapper

import finda.findavolunteer.adapter.`in`.volunteer.dto.request.CreateVolunteerRequest
import finda.findavolunteer.application.port.`in`.volunteer.dto.request.CreateVolunteerCommand
import finda.findavolunteer.application.port.`in`.volunteer.dto.request.VolunteerDateCommand

fun CreateVolunteerRequest.toCommand(): CreateVolunteerCommand =
    CreateVolunteerCommand(
        personnel = personnel,
        title = title,
        description = description,
        unitVolunteerTime = unitVolunteerTime,
        applicationDateCommand = VolunteerDateCommand(
            startDate = applicationDate.startDate,
            endDate = applicationDate.endDate
        ),
        workDateCommand = VolunteerDateCommand(
            startDate = workDate.startDate,
            endDate = workDate.endDate
        ),
        cycle = cycle,
        volunteerDateList = volunteerDate,
        teacherIdList = teachers,
        studentIdList = students,
        remindTime = remindTime,
        volunteerType = volunteerType,
        groupVolunteerType = groupVolunteerType,
        activityNameList = activity,
        weekdayList = weekdays,
        monthDate = monthDate
    )
