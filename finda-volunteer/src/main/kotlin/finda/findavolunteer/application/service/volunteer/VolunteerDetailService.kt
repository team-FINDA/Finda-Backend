package finda.findavolunteer.application.service.volunteer

import finda.findavolunteer.adapter.`in`.volunteer.dto.response.ActivityResponse
import finda.findavolunteer.adapter.`in`.volunteer.dto.response.StudentParticipationResponse
import finda.findavolunteer.adapter.`in`.volunteer.dto.response.VolunteerDetailResponse
import finda.findavolunteer.adapter.`in`.volunteer.dto.response.VolunteerScheduleResponse
import finda.findavolunteer.adapter.`in`.volunteer.dto.response.UserActivityResponse
import finda.findavolunteer.application.exception.UserNotFoundException
import finda.findavolunteer.application.port.`in`.volunteer.VolunteerDetailUseCase
import finda.findavolunteer.application.port.out.user.UserQueryPort
import finda.findavolunteer.application.port.out.volunteer.VolunteerDetailQueryPort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class VolunteerDetailService(
    private val volunteerDetailQueryPort: VolunteerDetailQueryPort,
    private val userQueryPort: UserQueryPort
) : VolunteerDetailUseCase {
    override fun execute(volunteerId: UUID): VolunteerDetailResponse {
        val volunteerDetail = volunteerDetailQueryPort.findDetailByIdOrThrow(volunteerId)
        val volunteer = volunteerDetail.volunteer

        return VolunteerDetailResponse(
            volunteerId = volunteer.id,
            title = volunteer.title,
            description = volunteer.description,
            status = volunteer.status,
            personnel = volunteer.personnel,
            unitVolunteerHours = volunteer.unitVolunteerHours,
            applicationStartDate = volunteer.applicationStartDate,
            applicationEndDate = volunteer.applicationEndDate,
            workStartDate = volunteer.workStartDate,
            workEndDate = volunteer.workEndDate,
            cycleType = volunteer.cycleType,
            weekdays = volunteerDetail.weekdays,
            monthDate = volunteerDetail.monthDate,
            remindTime = volunteer.remindTime,
            groupVolunteerType = volunteer.groupVolunteerType,
            volunteerType = volunteer.volunteerType,
            writerUserId = volunteer.userId,
            schedules = volunteerDetail.schedules.map {
                VolunteerScheduleResponse(
                    scheduleId = it.id,
                    scheduleDate = it.scheduleDate
                )
            },
            studentParticipations = volunteerDetail.studentParticipations.map {
                StudentParticipationResponse(
                    userId = it.userId,
                    name = userQueryPort.getUserName(it.userId) ?: throw UserNotFoundException,
                    status = it.status,
                    participatedAt = it.participatedAt
                )
            },
            activities = volunteerDetail.activities.map {
                ActivityResponse(
                    activityId = it.id,
                    activityName = it.activityName
                )
            },
            userActivities = volunteerDetail.userActivities.map {
                UserActivityResponse(
                    userId = it.userId,
                    userName = userQueryPort.getUserName(it.userId) ?: throw UserNotFoundException,
                    activityId = it.activityId
                )
            }
        )
    }
}
