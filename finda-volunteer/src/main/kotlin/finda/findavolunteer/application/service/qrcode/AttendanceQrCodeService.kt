package finda.findavolunteer.application.service.qrcode

import finda.findavolunteer.application.exception.participation.UserParticipationForbiddenException
import finda.findavolunteer.application.exception.qrcode.UsedQrCodeException
import finda.findavolunteer.application.facade.UserFacade
import finda.findavolunteer.application.port.`in`.qrcode.AttendanceQrCodeUseCase
import finda.findavolunteer.application.port.`in`.qrcode.dto.request.AttendanceQrCodeCommand
import finda.findavolunteer.application.port.out.participation.StudentParticipationQueryPort
import finda.findavolunteer.application.port.out.qrcode.QrCodeCommandPort
import finda.findavolunteer.application.port.out.qrcode.QrCodeQueryPort
import finda.findavolunteer.application.port.out.volunteer.VolunteerQueryPort
import finda.findavolunteer.application.port.out.volunteer.VolunteerRecordCommandPort
import finda.findavolunteer.domain.volunteer.model.VolunteerRecord
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AttendanceQrCodeService(
    private val qrCodeQueryPort: QrCodeQueryPort,
    private val qrCodeCommandPort: QrCodeCommandPort,
    private val studentParticipationQueryPort: StudentParticipationQueryPort,
    private val userFacade: UserFacade,
    private val volunteerRecordCommandPort: VolunteerRecordCommandPort,
    private val volunteerQueryPort: VolunteerQueryPort
) : AttendanceQrCodeUseCase {

    @Transactional
    override fun execute(request: AttendanceQrCodeCommand) {
        val userId = userFacade.currentUserId()
        val qrCode = qrCodeQueryPort.findByQrCodeForUpdateOrThrow(request.qrCode)
        val volunteer = volunteerQueryPort.findByIdOrThrow(qrCode.volunteerId)

        if (!studentParticipationQueryPort.existsByUserIdAndVolunteerId(userId, qrCode.volunteerId)) {
            throw UserParticipationForbiddenException
        }
        if (qrCode.studentId != null) {
            throw UsedQrCodeException
        }

        volunteerRecordCommandPort.save(
            VolunteerRecord(
                userId = userId,
                volunteerTime = volunteer.unitVolunteerHours,
                title = volunteer.title,
                volunteerId = volunteer.id
            )
        )

        qrCodeCommandPort.save(qrCode.updateStudentId(userId))
    }
}
