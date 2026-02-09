package finda.findaauth.application.port.`in`.student

import finda.findaauth.adapter.`in`.auth.dto.response.TokenResponse
import finda.findaauth.adapter.`in`.student.dto.request.StudentLoginRequest

interface StudentLoginUseCase {
    fun execute(request: StudentLoginRequest): TokenResponse
}
