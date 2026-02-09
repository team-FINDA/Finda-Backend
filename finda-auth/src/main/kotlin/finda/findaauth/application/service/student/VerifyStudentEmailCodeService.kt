package finda.findaauth.application.service.student

import finda.findaauth.adapter.`in`.student.dto.request.VerifyEmailCodeRequest
import finda.findaauth.adapter.`in`.student.dto.response.EmailVerificationResponse
import finda.findaauth.application.exception.mail.VerificationCodeMismatchException
import finda.findaauth.application.exception.mail.VerificationCodeNotFoundException
import finda.findaauth.application.port.`in`.student.VerifyEmailCodeUseCase
import finda.findaauth.global.mail.EmailVerificationStore
import finda.findaauth.global.util.StudentEmailUtils
import org.springframework.stereotype.Service

@Service
class VerifyStudentEmailCodeService(
    private val verificationStore: EmailVerificationStore
) : VerifyEmailCodeUseCase {

    override fun execute(
        request: VerifyEmailCodeRequest
    ): EmailVerificationResponse {
        val email = StudentEmailUtils.toFullEmail(request.email)
        val inputCode = request.code

        val savedCode = verificationStore.getCode(email)
            ?: throw VerificationCodeNotFoundException

        if (savedCode != inputCode) {
            throw VerificationCodeMismatchException
        }

        verificationStore.deleteCode(email)
        verificationStore.markAsVerified(email)

        return EmailVerificationResponse(
            success = true,
            message = "이메일 인증이 완료되었습니다"
        )
    }
}
