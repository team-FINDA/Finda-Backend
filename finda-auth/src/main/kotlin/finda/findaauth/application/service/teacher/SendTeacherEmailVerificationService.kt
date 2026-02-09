package finda.findaauth.application.service.teacher

import finda.findaauth.adapter.`in`.teacher.dto.request.SendEmailVerificationRequest
import finda.findaauth.adapter.`in`.auth.dto.response.EmailVerificationResponse
import finda.findaauth.application.exception.auth.InvalidPreAuthTokenException
import finda.findaauth.application.exception.mail.EmailSendLimitExceededException
import finda.findaauth.application.exception.user.EmailAlreadyExistsException
import finda.findaauth.application.port.`in`.teacher.SendEmailVerificationUseCase
import finda.findaauth.application.port.out.auth.AuthQueryPort
import finda.findaauth.application.port.out.user.UserQueryPort
import finda.findaauth.global.mail.EmailSendLimitStore
import finda.findaauth.global.mail.EmailService
import finda.findaauth.global.mail.EmailVerificationStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random

@Service
@Transactional
class SendTeacherEmailVerificationService(
    private val emailService: EmailService,
    private val userQueryPort: UserQueryPort,
    private val verificationStore: EmailVerificationStore,
    private val sendLimitStore: EmailSendLimitStore,
    private val authQueryPort: AuthQueryPort
) : SendEmailVerificationUseCase {

    override fun execute(
        preAuthToken: String,
        request: SendEmailVerificationRequest
    ): EmailVerificationResponse {
        if (!authQueryPort.isValid(preAuthToken)) {
            throw InvalidPreAuthTokenException
        }

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
