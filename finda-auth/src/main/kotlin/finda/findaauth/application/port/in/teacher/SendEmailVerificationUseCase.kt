package finda.findaauth.application.port.`in`.teacher

import finda.findaauth.adapter.`in`.teacher.dto.request.SendEmailVerificationRequest
import finda.findaauth.adapter.`in`.teacher.dto.response.EmailVerificationResponse

interface SendEmailVerificationUseCase {
    fun execute(preAuthToken: String, request: SendEmailVerificationRequest): EmailVerificationResponse
}
