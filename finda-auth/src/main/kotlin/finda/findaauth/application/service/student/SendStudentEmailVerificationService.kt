package finda.findaauth.application.service.student

import finda.findaauth.application.exception.mail.EmailSendLimitExceededException
import finda.findaauth.application.exception.user.EmailAlreadyExistsException
import finda.findaauth.application.port.`in`.auth.dto.response.EmailVerificationResult
import finda.findaauth.application.port.`in`.student.SendEmailVerificationUseCase
import finda.findaauth.application.port.`in`.student.dto.request.SendEmailVerificationCommand
import finda.findaauth.application.port.out.user.UserQueryPort
import finda.findaauth.global.mail.EmailSendLimitStore
import finda.findaauth.global.mail.EmailService
import finda.findaauth.global.mail.EmailVerificationStore
import finda.findaauth.global.mail.util.StudentEmailUtils
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class SendStudentEmailVerificationService(
    private val emailService: EmailService,
    private val userQueryPort: UserQueryPort,
    private val verificationStore: EmailVerificationStore,
    private val sendLimitStore: EmailSendLimitStore
) : SendEmailVerificationUseCase {

    override fun execute(
        command: SendEmailVerificationCommand
    ): EmailVerificationResult {
        val email = StudentEmailUtils.toFullEmail(command.accountId)

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
