package finda.findaauth.application.port.`in`.student

import finda.findaauth.adapter.`in`.student.dto.request.SendEmailVerificationRequest
import finda.findaauth.adapter.`in`.student.dto.response.EmailVerificationResponse

interface SendEmailVerificationUseCase {
    fun execute(request: SendEmailVerificationRequest): EmailVerificationResponse
}
