package finda.findaauth.application.port.`in`.teacher

import finda.findaauth.application.port.`in`.auth.dto.response.EmailVerificationResult
import finda.findaauth.application.port.`in`.teacher.dto.request.SendEmailVerificationCommand

interface SendEmailVerificationUseCase {
    fun execute(command: SendEmailVerificationCommand): EmailVerificationResult
}
