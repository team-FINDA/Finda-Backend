package finda.findaauth.application.service.student

import finda.findaauth.adapter.`in`.student.dto.request.SendEmailVerificationRequest
import finda.findaauth.adapter.`in`.student.dto.response.EmailVerificationResponse
import finda.findaauth.application.exception.teacher.EmailSendLimitExceededException
import finda.findaauth.application.exception.user.EmailAlreadyExistsException
import finda.findaauth.application.port.`in`.student.SendEmailVerificationUseCase
import finda.findaauth.application.port.out.user.UserQueryPort
import finda.findaauth.global.mail.EmailSendLimitStore
import finda.findaauth.global.mail.EmailService
import finda.findaauth.global.mail.EmailVerificationStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random

@Service
@Transactional
class SendStudentEmailVerificationService(
    private val emailService: EmailService,
    private val userQueryPort: UserQueryPort,
    private val verificationStore: EmailVerificationStore,
    private val sendLimitStore: EmailSendLimitStore
) : SendEmailVerificationUseCase {

    override fun execute(
        request: SendEmailVerificationRequest
    ): EmailVerificationResponse {
        val email = request.email

        if (userQueryPort.existsByEmail(email)) {
            throw EmailAlreadyExistsException
        }

        if (!sendLimitStore.canSend(email)) {
            throw EmailSendLimitExceededException
        }

        val code = generateCode()
        verificationStore.saveCode(email, code)

        sendLimitStore.incrementAndGet(email)

        emailService.sendVerificationCode(email, code)

        return EmailVerificationResponse(
            success = true,
            message = "인증코드가 발송되었습니다"
        )
    }

    private fun generateCode(): String {
        return Random.nextInt(100000, 1000000).toString()
    }
}
