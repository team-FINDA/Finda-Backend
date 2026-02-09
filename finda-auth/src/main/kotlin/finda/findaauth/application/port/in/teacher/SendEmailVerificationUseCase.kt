package finda.findaauth.application.port.`in`.teacher

import finda.findaauth.adapter.`in`.auth.dto.response.EmailVerificationResponse
import finda.findaauth.adapter.`in`.teacher.dto.request.SendEmailVerificationRequest

interface SendEmailVerificationUseCase {
    fun execute(preAuthToken: String, request: SendEmailVerificationRequest): EmailVerificationResponse
}
