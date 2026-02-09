package finda.findaauth.application.port.`in`.teacher

import finda.findaauth.adapter.`in`.teacher.dto.request.TeacherLoginRequest
import finda.findaauth.adapter.`in`.teacher.dto.response.TokenResponse

interface TeacherLoginUseCase {
    fun execute(request: TeacherLoginRequest): TokenResponse
}
