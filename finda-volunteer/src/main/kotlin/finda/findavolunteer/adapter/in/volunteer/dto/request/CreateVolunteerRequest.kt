package finda.findavolunteer.adapter.`in`.volunteer.dto.request

import finda.findavolunteer.domain.volunteer.enum.CycleType
import finda.findavolunteer.domain.volunteer.enum.GroupVolunteerType
import finda.findavolunteer.domain.volunteer.enum.VolunteerType
import finda.findavolunteer.domain.volunteer.enum.Weekday
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class CreateVolunteerRequest(
    val personnel: Int,
    val title: String,
    val description: String,
    val unitVolunteerTime: Float,
    val applicationDate: VolunteerDate,
    val workDate: VolunteerDate,
    val cycle: CycleType = CycleType.NONE,
    val volunteerDate: List<LocalDate>,
    val teachers: List<UUID>,
    val students: List<UUID>, // 확정인 학생만 보냄 -> Applied
    val remindTime: LocalTime,
    val volunteerType: VolunteerType,
    val groupVolunteerType: GroupVolunteerType,
    val activity: List<String>,
    val weekdays: List<Weekday>,
    val monthDate: Int? // 달마다 반복할 일 ex) 15 -> 1월 15일, 2월 15일 ..
)

data class VolunteerDate(
    val startDate: LocalDate,
    val endDate: LocalDate
)
