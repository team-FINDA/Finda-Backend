package finda.findavolunteer.adapter.`in`.volunteer.dto.response

import finda.findavolunteer.domain.participation.enum.ParticipationStatus
import finda.findavolunteer.domain.volunteer.enum.CycleType
import finda.findavolunteer.domain.volunteer.enum.GroupVolunteerType
import finda.findavolunteer.domain.volunteer.enum.VolunteerStatus
import finda.findavolunteer.domain.volunteer.enum.VolunteerType
import finda.findavolunteer.domain.volunteer.enum.Weekday
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.UUID

data class VolunteerDetailResponse(
    val volunteerId: UUID,
    val title: String,
    val description: String,
    val status: VolunteerStatus,
    val personnel: Int,
    val unitVolunteerHours: Float,
    val applicationStartDate: LocalDate,
    val applicationEndDate: LocalDate,
    val workStartDate: LocalDate,
    val workEndDate: LocalDate,
    val cycleType: CycleType,
    val weekdays: List<Weekday>,
    val monthDate: Int?,
    val remindTime: LocalTime,
    val groupVolunteerType: GroupVolunteerType,
    val volunteerType: VolunteerType,
    val writerUserId: UUID,
    val schedules: List<VolunteerScheduleResponse>,
    val studentParticipations: List<StudentParticipationResponse>,
    val activities: List<ActivityResponse>,
    val userActivities: List<UserActivityResponse>
)

data class VolunteerScheduleResponse(
    val scheduleId: UUID,
    val scheduleDate: LocalDate
)

data class StudentParticipationResponse(
    val userId: UUID,
    val name: String,
    val status: ParticipationStatus,
    val participatedAt: LocalDateTime
)

data class ActivityResponse(
    val activityId: UUID,
    val activityName: String
)

data class UserActivityResponse(
    val userId: UUID,
    val userName: String,
    val activityId: UUID
)
