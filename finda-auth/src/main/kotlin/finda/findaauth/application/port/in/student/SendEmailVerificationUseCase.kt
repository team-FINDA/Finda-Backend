package finda.findaauth.application.port.`in`.student

import finda.findaauth.application.port.`in`.auth.dto.response.EmailVerificationResult
import finda.findaauth.application.port.`in`.student.dto.request.SendEmailVerificationCommand

interface SendEmailVerificationUseCase {
    fun execute(command: SendEmailVerificationCommand): EmailVerificationResult
}
