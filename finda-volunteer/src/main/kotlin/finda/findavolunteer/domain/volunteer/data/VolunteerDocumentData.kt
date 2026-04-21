package finda.findavolunteer.domain.volunteer.data

import finda.findavolunteer.domain.volunteer.enum.GroupVolunteerType
import finda.findavolunteer.domain.volunteer.enum.VolunteerType
import java.time.LocalDate
import java.util.UUID

data class VolunteerDocumentData(
    val volunteerId: UUID,
    val title: String,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val teacherName: String,
    val groupVolunteerType: GroupVolunteerType,
    val volunteerType: VolunteerType,
    val participants: List<ParticipantInfo>
)

data class ParticipantInfo(
    val userId: String,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val recognizedHours: Float
)
