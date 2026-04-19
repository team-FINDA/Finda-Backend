package finda.findavolunteer.application.service.qrcode

import finda.findavolunteer.application.exception.participation.TeacherParticipationForbiddenException
import finda.findavolunteer.application.facade.UserFacade
import finda.findavolunteer.application.port.`in`.qrcode.CreateQrCodeUseCase
import finda.findavolunteer.application.port.`in`.qrcode.dto.request.CreateQrCodeCommand
import finda.findavolunteer.application.port.out.participation.TeacherParticipationQueryPort
import finda.findavolunteer.application.port.out.qrcode.QrCodeCommandPort
import finda.findavolunteer.application.port.out.qrcode.QrCodeQueryPort
import finda.findavolunteer.domain.qrcode.model.QrCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class CreateQrCodeService(
    private val qrCodeCommandPort: QrCodeCommandPort,
    private val qrCodeQueryPort: QrCodeQueryPort,
    private val userFacade: UserFacade,
    private val teacherParticipationQueryPort: TeacherParticipationQueryPort
) : CreateQrCodeUseCase {
    @Transactional
    override fun execute(request: CreateQrCodeCommand): String {
        val userId = userFacade.currentUserId()
        var qrcode = makeCode()

        // QR코드가 중복되지 않을 때까지 생성
        while (qrCodeQueryPort.existsQrCode(qrcode)) {
            qrcode = makeCode()
        }

        if (!teacherParticipationQueryPort.existsByTeacherIdAndVolunteerId(userId, request.volunteerId)) {
            throw TeacherParticipationForbiddenException
        }

        qrCodeCommandPort.save(
            QrCode(
                volunteerId = request.volunteerId,
                code = qrcode,
                generatedAt = LocalDateTime.now(),
                isUsed = false,
                teacherId = userId
            )
        )

        return qrcode
    }

    // 영어 소문자 + 대문자 + 숫자를 합하여 6자리 문자열 반환
    private fun makeCode(): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..6)
            .map { chars.random() }
            .joinToString("")
    }
}
