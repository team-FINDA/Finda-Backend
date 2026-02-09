package finda.findaauth.application.port.`in`.student

import finda.findaauth.adapter.`in`.student.dto.request.VerifyEmailCodeRequest
import finda.findaauth.adapter.`in`.student.dto.response.EmailVerificationResponse

interface VerifyEmailCodeUseCase {
    fun execute(request: VerifyEmailCodeRequest): EmailVerificationResponse
}
