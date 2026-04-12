package finda.findavolunteer.application.service.volunteer

import finda.findavolunteer.application.facade.UserFacade
import finda.findavolunteer.application.port.`in`.volunteer.CreateVolunteerUseCase
import finda.findavolunteer.application.port.`in`.volunteer.dto.request.CreateVolunteerCommand
import finda.findavolunteer.application.port.out.activity.ActivityCommandPort
import finda.findavolunteer.application.port.out.participation.StudentParticipationCommandPort
import finda.findavolunteer.application.port.out.participation.TeacherParticipationCommandPort
import finda.findavolunteer.application.port.out.volunteer.VolunteerCommandPort
import finda.findavolunteer.application.port.out.volunteer.VolunteerScheduleCommandPort
import finda.findavolunteer.domain.activity.model.Activity
import finda.findavolunteer.domain.participation.enum.ParticipationStatus
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
    override fun execute(command: CreateVolunteerCommand) {
        val userId = userFacade.currentUserId()

        val volunteer = volunteerCommandPort.save(
            Volunteer(
                status = VolunteerStatus.APPLICATION,
                personnel = command.personnel,
                title = command.title,
                description = command.description,
                unitVolunteerHours = command.unitVolunteerTime,
                applicationStartDate = command.applicationDateCommand.startDate,
                applicationEndDate = command.applicationDateCommand.endDate,
                workStartDate = command.workDateCommand.startDate,
                workEndDate = command.workDateCommand.endDate,
                cycleType = command.cycle,
                userId = userId,
                remindTime = command.remindTime,
                groupVolunteerType = command.groupVolunteerType,
                volunteerType = command.volunteerType
            )
        )

        command.volunteerDateList.forEach {
            volunteerScheduleCommandPort.save(
                VolunteerSchedule(
                    scheduleDate = it,
                    volunteerId = volunteer.id
                )
            )
        }

        when (command.cycle) {
            CycleType.NONE -> Unit
            CycleType.WEEK -> {
                command.weekdayList.forEach {
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
                        day = command.monthDate!!
                    )
                )
            }
        }

        command.activityNameList.forEach {
            activityCommandPort.save(
                Activity(
                    activityName = it,
                    volunteerId = volunteer.id
                )
            )
        }

        command.studentIdList.forEach {
            studentParticipationCommandPort.save(
                StudentParticipation(
                    volunteerId = volunteer.id,
                    status = ParticipationStatus.APPLIED,
                    participatedAt = LocalDateTime.now(),
                    userId = it
                )
            )
        }

        command.teacherIdList.forEach {
            teacherParticipationCommandPort.save(
                TeacherParticipation(
                    volunteerId = volunteer.id,
                    userId = it
                )
            )
        }

        // TODO("outbox로 활동시간, 모집기간 -> 배치에 이벤트로 보내기")
        // TODO("outbox로 활동일, remind time 배치에 이벤트로 보내기")
        // TODO("debezium 여러 테이블 읽어서 이벤트 발행 검색")
    }
}
