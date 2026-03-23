package finda.findavolunteer.application.service.volunteer

import finda.findavolunteer.adapter.`in`.volunteer.dto.request.CreateVolunteerRequest
import finda.findavolunteer.application.facade.UserFacade
import finda.findavolunteer.application.port.`in`.volunteer.CreateVolunteerUseCase
import finda.findavolunteer.application.port.out.activity.ActivityCommandPort
import finda.findavolunteer.application.port.out.participation.StudentParticipationCommandPort
import finda.findavolunteer.application.port.out.participation.TeacherParticipationCommandPort
import finda.findavolunteer.application.port.out.volunteer.VolunteerCommandPort
import finda.findavolunteer.application.port.out.volunteer.VolunteerScheduleCommandPort
import finda.findavolunteer.domain.activity.model.Activity
import finda.findavolunteer.domain.participation.enum.ParticitationStatus
import finda.findavolunteer.domain.participation.model.StudentParticipation
import finda.findavolunteer.domain.participation.model.TeacherParticipation
import finda.findavolunteer.domain.volunteer.enum.CycleType
import finda.findavolunteer.domain.volunteer.enum.VolunteerStatus
import finda.findavolunteer.domain.volunteer.model.Volunteer
import finda.findavolunteer.domain.volunteer.model.VolunteerSchedule
import finda.findavolunteer.domain.volunteer.model.recurrence.ActivityRecurrenceMonth
import finda.findavolunteer.domain.volunteer.model.recurrence.ActivityRecurrenceWeek
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CreateVolunteerService(
    val volunteerCommandPort: VolunteerCommandPort,
    val userFacade: UserFacade,
    val volunteerScheduleCommandPort: VolunteerScheduleCommandPort,
    val activityCommandPort: ActivityCommandPort,
    val studentParticipationCommandPort: StudentParticipationCommandPort,
    val teacherParticipationCommandPort: TeacherParticipationCommandPort
) : CreateVolunteerUseCase {
    @Transactional
    override fun execute(request: CreateVolunteerRequest) {
        val userId = userFacade.currentUserId()

        val volunteer = volunteerCommandPort.save(
            Volunteer(
                status = VolunteerStatus.APPLICATION,
                personnel = request.personal,
                title = request.title,
                description = request.description,
                unitVolunteerHours = request.unitVolunteerTime,
                applicationStartDate = request.applicationDate.startDate,
                applicationEndDate = request.applicationDate.endDate,
                workStartDate = request.workDate.startDate,
                workEndDate = request.workDate.endDate,
                cycleType = request.cycle,
                userId = userId,
                remindTime = request.remindTime,
                groupVolunteerType = request.groupVolunteerType,
                volunteerType = request.volunteerType
            )
        )

        request.volunteerDate.forEach {
            volunteerScheduleCommandPort.save(
                VolunteerSchedule(
                    scheduleDate = it,
                    volunteerId = volunteer.id
                )
            )
        }

        when (request.cycle) {
            CycleType.NONE -> Unit
            CycleType.WEEK -> {
                request.weekdays.forEach {
                    volunteerCommandPort.saveWeekRecurrence(
                        ActivityRecurrenceWeek(
                            weekday = it,
                            volunteerId = volunteer.id
                        )
                    )
                }
            }
            CycleType.MONTH -> {
                volunteerCommandPort.saveMonthRecurrence(
                    ActivityRecurrenceMonth(
                        volunteerId = volunteer.id,
                        day = request.monthDate!!
                    )
                )
            }
        }

        request.activity.forEach {
            activityCommandPort.save(
                Activity(
                    activityName = it,
                    volunteerId = volunteer.id
                )
            )
        }

        request.students.forEach {
            studentParticipationCommandPort.save(
                StudentParticipation(
                    volunteerId = volunteer.id,
                    status = ParticitationStatus.APPLIED,
                    participatedAt = LocalDateTime.now(),
                    userId = it
                )
            )
        }

        request.teachers.forEach {
            teacherParticipationCommandPort.save(
                TeacherParticipation(
                    volunteerId = volunteer.id,
                    userId = it
                )
            )
        }

        // TODO("outbox로 활동시간, 모집기간 -> 배치에 이벤트로 보내기")
        // TODO("outbox로 활동일, remind time 배치에 이벤트로 보내기")
    }
}
