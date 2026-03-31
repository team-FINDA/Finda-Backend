package finda.findavolunteer.domain.volunteer.data

import java.time.LocalDate
import java.util.UUID

data class VolunteerDocumentData(
    val volunteerId: UUID,
    val title: String,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val teacherName: String,
    val participants: List<ParticipantInfo>
)

data class ParticipantInfo(
    val userId: String,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val recognizedHours: Int
)
