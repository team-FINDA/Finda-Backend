package finda.findaauth.application.service.teacher

import finda.findaauth.application.exception.auth.InvalidPreAuthTokenException
import finda.findaauth.application.exception.mail.EmailSendLimitExceededException
import finda.findaauth.application.exception.user.EmailAlreadyExistsException
import finda.findaauth.application.port.`in`.auth.dto.response.EmailVerificationResult
import finda.findaauth.application.port.`in`.teacher.SendEmailVerificationUseCase
import finda.findaauth.application.port.`in`.teacher.dto.request.SendEmailVerificationCommand
import finda.findaauth.application.port.out.teacher.TeacherPreAuthQueryPort
import finda.findaauth.application.port.out.user.UserQueryPort
import finda.findaauth.global.mail.EmailSendLimitStore
import finda.findaauth.global.mail.EmailService
import finda.findaauth.global.mail.EmailVerificationStore
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class SendTeacherEmailVerificationService(
    private val emailService: EmailService,
    private val userQueryPort: UserQueryPort,
    private val verificationStore: EmailVerificationStore,
    private val sendLimitStore: EmailSendLimitStore,
    private val teacherPreAuthQueryPort: TeacherPreAuthQueryPort
) : SendEmailVerificationUseCase {

    override fun execute(
        command: SendEmailVerificationCommand
    ): EmailVerificationResult {
        if (!teacherPreAuthQueryPort.isValid(command.preAuthToken)) {
            throw InvalidPreAuthTokenException
        }

        val email = command.email

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

        return EmailVerificationResult(
            success = true,
            message = "인증코드가 발송되었습니다"
        )
    }

    private fun generateCode(): String {
        return Random.nextInt(100000, 1000000).toString()
    }
}
