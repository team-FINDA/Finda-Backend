package finda.findaauth.application.port.`in`.teacher

import finda.findaauth.adapter.`in`.auth.dto.response.TokenResponse
import finda.findaauth.adapter.`in`.teacher.dto.request.TeacherLoginRequest

interface TeacherLoginUseCase {
    fun execute(request: TeacherLoginRequest): TokenResponse
}
