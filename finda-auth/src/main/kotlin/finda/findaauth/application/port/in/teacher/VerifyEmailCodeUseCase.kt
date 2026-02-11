package finda.findaauth.application.port.`in`.teacher

import finda.findaauth.application.port.`in`.auth.dto.response.EmailVerificationResult
import finda.findaauth.application.port.`in`.teacher.dto.request.VerifyEmailCodeCommand

interface VerifyEmailCodeUseCase {
    fun execute(command: VerifyEmailCodeCommand): EmailVerificationResult
}
