package finda.findaauth.application.port.`in`.teacher

import finda.findaauth.adapter.`in`.auth.dto.response.EmailVerificationResponse
import finda.findaauth.adapter.`in`.teacher.dto.request.VerifyEmailCodeRequest

interface VerifyEmailCodeUseCase {
    fun execute(preAuthToken: String, request: VerifyEmailCodeRequest): EmailVerificationResponse
}
