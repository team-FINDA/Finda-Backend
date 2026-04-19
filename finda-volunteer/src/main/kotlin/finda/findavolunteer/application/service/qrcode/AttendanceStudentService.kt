package finda.findavolunteer.application.service.qrcode

import finda.findavolunteer.application.exception.participation.UserParticipationForbiddenException
import finda.findavolunteer.application.port.`in`.qrcode.AttendanceStudentsUseCase
import finda.findavolunteer.application.port.`in`.qrcode.dto.request.AttendanceStudentsCommand
import finda.findavolunteer.application.port.out.participation.StudentParticipationQueryPort
import finda.findavolunteer.application.port.out.user.UserCommandPort
import finda.findavolunteer.application.port.out.volunteer.VolunteerQueryPort
import finda.findavolunteer.application.port.out.volunteer.VolunteerRecordCommandPort
import finda.findavolunteer.domain.volunteer.model.VolunteerRecord
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class AttendanceStudentService(
    private val volunteerQueryPort: VolunteerQueryPort,
    private val studentParticipationQueryPort: StudentParticipationQueryPort,
    private val volunteerRecordCommandPort: VolunteerRecordCommandPort,
    private val userCommandPort: UserCommandPort
) : AttendanceStudentsUseCase {

    @Transactional
    override fun execute(request: AttendanceStudentsCommand) {
        val volunteer = volunteerQueryPort.findByIdOrThrow(request.volunteerId)

        request.userIds.forEach { userId ->
            attendStudent(userId, request.volunteerId, volunteer.title, volunteer.unitVolunteerHours)
        }
    }

    private fun attendStudent(userId: UUID, volunteerId: UUID, title: String, unitVolunteerHours: Float) {
        if (!studentParticipationQueryPort.existsByUserIdAndVolunteerId(userId, volunteerId)) {
            throw UserParticipationForbiddenException
        }

        volunteerRecordCommandPort.save(
            VolunteerRecord(
                userId = userId,
                volunteerTime = unitVolunteerHours,
                title = title,
                volunteerId = volunteerId
            )
        )

        userCommandPort.addVolunteerTime(userId, unitVolunteerHours)
    }
}
