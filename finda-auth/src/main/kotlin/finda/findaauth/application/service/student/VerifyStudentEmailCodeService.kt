package finda.findaauth.application.service.student

import finda.findaauth.application.exception.mail.VerificationCodeMismatchException
import finda.findaauth.application.exception.mail.VerificationCodeNotFoundException
import finda.findaauth.application.port.`in`.auth.dto.response.EmailVerificationResult
import finda.findaauth.application.port.`in`.student.VerifyEmailCodeUseCase
import finda.findaauth.application.port.`in`.student.dto.request.VerifyEmailCodeCommand
import finda.findaauth.global.mail.EmailVerificationStore
import finda.findaauth.global.mail.util.StudentEmailUtils
import org.springframework.stereotype.Service

@Service
class VerifyStudentEmailCodeService(
    private val verificationStore: EmailVerificationStore
) : VerifyEmailCodeUseCase {

    override fun execute(
        command: VerifyEmailCodeCommand
    ): EmailVerificationResult {
        val email = StudentEmailUtils.toFullEmail(command.accountId)
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
