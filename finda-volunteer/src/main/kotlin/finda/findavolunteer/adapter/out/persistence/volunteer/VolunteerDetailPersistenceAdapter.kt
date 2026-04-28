package finda.findavolunteer.adapter.out.persistence.volunteer

import finda.findavolunteer.adapter.out.persistence.activity.repository.ActivityRepository
import finda.findavolunteer.adapter.out.persistence.activity.repository.UserActivityRepository
import finda.findavolunteer.adapter.out.persistence.participation.repository.StudentParticipationRepository
import finda.findavolunteer.adapter.out.persistence.volunteer.repository.ActivityRecurrenceMonthRepository
import finda.findavolunteer.adapter.out.persistence.volunteer.repository.ActivityRecurrenceWeekRepository
import finda.findavolunteer.adapter.out.persistence.volunteer.repository.VolunteerRepository
import finda.findavolunteer.adapter.out.persistence.volunteer.repository.VolunteerScheduleRepository
import finda.findavolunteer.application.exception.volunteer.VolunteerNotFoundException
import finda.findavolunteer.application.port.out.volunteer.VolunteerDetailQueryPort
import finda.findavolunteer.domain.activity.model.Activity
import finda.findavolunteer.domain.activity.model.UserActivity
import finda.findavolunteer.domain.participation.model.StudentParticipation
import finda.findavolunteer.domain.volunteer.enum.CycleType
import finda.findavolunteer.domain.volunteer.model.Volunteer
import finda.findavolunteer.domain.volunteer.model.VolunteerDetail
import finda.findavolunteer.domain.volunteer.model.VolunteerSchedule
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class VolunteerDetailPersistenceAdapter(
    private val volunteerRepository: VolunteerRepository,
    private val volunteerScheduleRepository: VolunteerScheduleRepository,
    private val studentParticipationRepository: StudentParticipationRepository,
    private val activityRepository: ActivityRepository,
    private val userActivityRepository: UserActivityRepository,
    private val activityRecurrenceWeekRepository: ActivityRecurrenceWeekRepository,
    private val activityRecurrenceMonthRepository: ActivityRecurrenceMonthRepository
) : VolunteerDetailQueryPort {
    override fun findDetailByIdOrThrow(volunteerId: UUID): VolunteerDetail {
        val volunteerEntity = volunteerRepository.findByIdOrNull(volunteerId)
            ?: throw VolunteerNotFoundException

        val schedules = volunteerScheduleRepository.findAllByVolunteerIdOrderByScheduleDateAsc(volunteerId)
            .map {
                VolunteerSchedule(
                    id = requireNotNull(it.id),
                    scheduleDate = it.scheduleDate,
                    volunteerId = volunteerId
                )
            }

        val studentParticipations = studentParticipationRepository.findAllByVolunteerId(volunteerId)
            .map {
                StudentParticipation(
                    id = requireNotNull(it.id),
                    volunteerId = volunteerId,
                    status = it.status,
                    participatedAt = it.participatedAt,
                    userId = it.userId
                )
            }

        val activities = activityRepository.findAllByVolunteerId(volunteerId)
            .map {
                Activity(
                    id = requireNotNull(it.id),
                    activityName = it.activityName,
                    volunteerId = volunteerId
                )
            }

        val userActivities = userActivityRepository.findAllWithActivityByVolunteerId(volunteerId)
            .map {
                UserActivity(
                    id = requireNotNull(it.id),
                    activityId = requireNotNull(it.activity?.id),
                    userId = it.userId
                )
            }

        val weekdays = activityRecurrenceWeekRepository.findAllByVolunteerId(volunteerId)
            .map { it.weekday }

        val monthDate = activityRecurrenceMonthRepository.findByVolunteerId(volunteerId)?.day

        val volunteer = Volunteer(
            id = requireNotNull(volunteerEntity.id),
            status = volunteerEntity.status,
            personnel = volunteerEntity.personnel,
            title = volunteerEntity.title,
            description = volunteerEntity.description,
            unitVolunteerHours = volunteerEntity.unitVolunteerHours,
            applicationStartDate = volunteerEntity.applicationStartDate,
            applicationEndDate = volunteerEntity.applicationEndDate,
            workStartDate = volunteerEntity.workStartDate,
            workEndDate = volunteerEntity.workEndDate,
            cycleType = volunteerEntity.cycleType ?: CycleType.NONE,
            userId = volunteerEntity.userId,
            remindTime = volunteerEntity.remindTime,
            groupVolunteerType = volunteerEntity.groupVolunteerType,
            volunteerType = volunteerEntity.volunteerType
        )

        return VolunteerDetail(
            volunteer = volunteer,
            schedules = schedules,
            studentParticipations = studentParticipations,
            activities = activities,
            userActivities = userActivities,
            weekdays = weekdays,
            monthDate = monthDate
        )
    }
}
