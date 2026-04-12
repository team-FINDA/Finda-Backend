package finda.findavolunteer.application.port.`in`.volunteer.dto.request

import finda.findavolunteer.domain.volunteer.enum.CycleType
import finda.findavolunteer.domain.volunteer.enum.GroupVolunteerType
import finda.findavolunteer.domain.volunteer.enum.VolunteerType
import finda.findavolunteer.domain.volunteer.enum.Weekday
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class CreateVolunteerCommand(
    val personnel: Int,
    val title: String,
    val description: String,
    val unitVolunteerTime: Float,
    val applicationDateCommand: VolunteerDateCommand,
    val workDateCommand: VolunteerDateCommand,
    val cycle: CycleType,
    val volunteerDateList: List<LocalDate>,
    val teacherIdList: List<UUID>,
    val studentIdList: List<UUID>,
    val remindTime: LocalTime,
    val volunteerType: VolunteerType,
    val groupVolunteerType: GroupVolunteerType,
    val activityNameList: List<String>,
    val weekdayList: List<Weekday>,
    val monthDate: Int?
)

data class VolunteerDateCommand(
    val startDate: LocalDate,
    val endDate: LocalDate
)
