package finda.findavolunteer.application.service.volunteer

import finda.findavolunteer.application.exception.UserNotFoundException
import finda.findavolunteer.application.port.`in`.volunteer.VolunteerDetailUseCase
import finda.findavolunteer.application.port.`in`.volunteer.dto.response.ActivityResult
import finda.findavolunteer.application.port.`in`.volunteer.dto.response.StudentParticipationResult
import finda.findavolunteer.application.port.`in`.volunteer.dto.response.UserActivityResult
import finda.findavolunteer.application.port.`in`.volunteer.dto.response.VolunteerDetailResult
import finda.findavolunteer.application.port.`in`.volunteer.dto.response.VolunteerScheduleResult
import finda.findavolunteer.application.port.out.user.UserQueryPort
import finda.findavolunteer.application.port.out.volunteer.VolunteerDetailQueryPort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class VolunteerDetailService(
    private val volunteerDetailQueryPort: VolunteerDetailQueryPort,
    private val userQueryPort: UserQueryPort
) : VolunteerDetailUseCase {
    override fun execute(volunteerId: UUID): VolunteerDetailResult {
        val volunteerDetail = volunteerDetailQueryPort.findDetailByIdOrThrow(volunteerId)
        val volunteer = volunteerDetail.volunteer

        return VolunteerDetailResult(
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
            weekdayList = volunteerDetail.weekdays,
            monthDate = volunteerDetail.monthDate,
            remindTime = volunteer.remindTime,
            groupVolunteerType = volunteer.groupVolunteerType,
            volunteerType = volunteer.volunteerType,
            writerUserId = volunteer.userId,
            scheduleResultList = volunteerDetail.schedules.map {
                VolunteerScheduleResult(
                    scheduleId = it.id,
                    scheduleDate = it.scheduleDate
                )
            },
            studentParticipationResultList = volunteerDetail.studentParticipations.map {
                StudentParticipationResult(
                    userId = it.userId,
                    name = userQueryPort.getUserName(it.userId) ?: throw UserNotFoundException,
                    status = it.status,
                    participatedAt = it.participatedAt
                )
            },
            activityResultList = volunteerDetail.activities.map {
                ActivityResult(
                    activityId = it.id,
                    activityName = it.activityName
                )
            },
            userActivityResultList = volunteerDetail.userActivities.map {
                UserActivityResult(
                    userId = it.userId,
                    userName = userQueryPort.getUserName(it.userId) ?: throw UserNotFoundException,
                    activityId = it.activityId
                )
            }
        )
    }
}
