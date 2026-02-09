package finda.findaauth.application.port.`in`.teacher

import finda.findaauth.adapter.`in`.teacher.dto.request.VerifyEmailCodeRequest
import finda.findaauth.adapter.`in`.auth.dto.response.EmailVerificationResponse

interface VerifyEmailCodeUseCase {
    fun execute(preAuthToken: String, request: VerifyEmailCodeRequest): EmailVerificationResponse
}
