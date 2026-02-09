package finda.findaauth.application.port.`in`.student

import finda.findaauth.adapter.`in`.student.dto.request.SendEmailVerificationRequest

interface SendEmailVerificationUseCase {
    fun execute(request: SendEmailVerificationRequest): EmailVerificationResponse
}
