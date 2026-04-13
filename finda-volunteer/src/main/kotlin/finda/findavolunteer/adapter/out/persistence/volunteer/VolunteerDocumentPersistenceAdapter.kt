package finda.findavolunteer.adapter.out.persistence.volunteer

import finda.findavolunteer.adapter.out.grpc.AuthGrpcClient
import finda.findavolunteer.adapter.out.grpc.StudentGrpcClient
import finda.findavolunteer.adapter.out.persistence.participation.repository.StudentParticipationRepository
import finda.findavolunteer.adapter.out.persistence.volunteer.repository.VolunteerRecordRepository
import finda.findavolunteer.adapter.out.persistence.volunteer.repository.VolunteerRepository
import finda.findavolunteer.application.exception.grpc.StudentInfoNotFoundException
import finda.findavolunteer.application.exception.volunteer.VolunteerNotFoundException
import finda.findavolunteer.application.port.out.volunteer.LoadVolunteerDocumentPort
import finda.findavolunteer.domain.volunteer.data.ParticipantInfo
import finda.findavolunteer.domain.volunteer.data.VolunteerDocumentData
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class VolunteerDocumentPersistenceAdapter(
    private val volunteerRepository: VolunteerRepository,
    private val studentParticipationRepository: StudentParticipationRepository,
    private val volunteerRecordRepository: VolunteerRecordRepository,
    private val studentGrpcClient: StudentGrpcClient,
    private val authGrpcClient: AuthGrpcClient
) : LoadVolunteerDocumentPort {

    override fun loadVolunteerDocumentData(volunteerId: UUID): VolunteerDocumentData {
        val volunteer = volunteerRepository.findById(volunteerId)
            .orElseThrow { VolunteerNotFoundException }

        val participations =
            studentParticipationRepository.findAllByVolunteerId(volunteerId)

        val participantUserIds = participations.map { it.userId }

        val studentsInfo = studentGrpcClient.getStudentsInfo(participantUserIds)

        val studentMap = studentsInfo.studentsInfoList
            .associateBy { UUID.fromString(it.userId) }

        val records = volunteerRecordRepository.findAllByVolunteerId(volunteerId)

        val recordMap = records.associateBy { it.userId }

        val participants = participantUserIds.map { userId ->
            val info = studentMap[userId]
                ?: throw StudentInfoNotFoundException

            ParticipantInfo(
                userId = userId.toString(),
                name = info.userInfo.name,
                grade = info.userInfo.grade,
                classNum = info.userInfo.classNum,
                num = info.userInfo.num,
                recognizedHours = recordMap[userId]?.volunteerTime ?: 0
            )
        }.sortedWith(
            compareBy(
                ParticipantInfo::grade,
                ParticipantInfo::classNum,
                ParticipantInfo::num
            )
        )

        val teacherName = runCatching {
            authGrpcClient.getUserName(volunteer.userId)
        }.getOrDefault("미확인")

        return VolunteerDocumentData(
            volunteerId = volunteerId,
            title = volunteer.title,
            description = volunteer.description,
            startDate = volunteer.workStartDate,
            endDate = volunteer.workEndDate,
            teacherName = teacherName,
            groupVolunteerType = volunteer.groupVolunteerType,
            volunteerType = volunteer.volunteerType,
            participants = participants
        )
    }
}
