package finda.findavolunteer.domain.volunteer.model

import finda.findavolunteer.domain.volunteer.enum.CycleType
import finda.findavolunteer.domain.volunteer.enum.GroupVolunteerType
import finda.findavolunteer.domain.volunteer.enum.VolunteerStatus
import finda.findavolunteer.domain.volunteer.enum.VolunteerType
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class Volunteer(
    val id: UUID = UUID(0, 0),
    val status: VolunteerStatus,
    val personnel: Int,
    val title: String,
    val description: String,
    val unitVolunteerHours: Float,
    val applicationStartDate: LocalDate,
    val applicationEndDate: LocalDate,
    val workStartDate: LocalDate,
    val workEndDate: LocalDate,
    val cycleType: CycleType,
    val userId: UUID,
    val remindTime: LocalTime,
    val groupVolunteerType: GroupVolunteerType,
    val volunteerType: VolunteerType
)
