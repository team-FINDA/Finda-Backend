package finda.findaauth.application.port.`in`.student

import finda.findaauth.adapter.`in`.student.dto.request.StudentLoginRequest
import finda.findaauth.adapter.`in`.teacher.dto.response.TokenResponse

interface StudentLoginUseCase {
    fun execute(request: StudentLoginRequest): TokenResponse
}
