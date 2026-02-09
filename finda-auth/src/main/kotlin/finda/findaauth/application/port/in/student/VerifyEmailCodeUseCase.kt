package finda.findaauth.application.port.`in`.student

import finda.findaauth.adapter.`in`.student.dto.request.VerifyEmailCodeRequest

interface VerifyEmailCodeUseCase {
    fun execute(request: VerifyEmailCodeRequest): EmailVerificationResponse
}
