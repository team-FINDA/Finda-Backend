package finda.findaauth.application.service.teacher

import finda.findaauth.application.exception.auth.InvalidPreAuthTokenException
import finda.findaauth.application.exception.mail.VerificationCodeMismatchException
import finda.findaauth.application.exception.mail.VerificationCodeNotFoundException
import finda.findaauth.application.port.`in`.auth.dto.response.EmailVerificationResult
import finda.findaauth.application.port.`in`.teacher.VerifyEmailCodeUseCase
import finda.findaauth.application.port.`in`.teacher.dto.request.VerifyEmailCodeCommand
import finda.findaauth.application.port.out.teacher.TeacherPreAuthQueryPort
import finda.findaauth.global.mail.EmailVerificationStore
import org.springframework.stereotype.Service

@Service
class VerifyTeacherEmailCodeService(
    private val verificationStore: EmailVerificationStore,
    private val teacherPreAuthQueryPort: TeacherPreAuthQueryPort
) : VerifyEmailCodeUseCase {

    override fun execute(
        command: VerifyEmailCodeCommand
    ): EmailVerificationResult {
        if (!teacherPreAuthQueryPort.isValid(command.preAuthToken)) {
            throw InvalidPreAuthTokenException
        }

        val email = command.email
        val inputCode = command.code

        val savedCode = verificationStore.getCode(email)
            ?: throw VerificationCodeNotFoundException

        if (savedCode != inputCode) {
            throw VerificationCodeMismatchException
        }

        verificationStore.deleteCode(email)
        verificationStore.markAsVerified(email)

        return EmailVerificationResult(
            success = true,
            message = "이메일 인증이 완료되었습니다"
        )
    }
}
