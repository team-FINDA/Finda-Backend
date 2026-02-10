package finda.findaauth.application.port.`in`.teacher

import finda.findaauth.application.port.`in`.auth.dto.response.TokenResult
import finda.findaauth.application.port.`in`.teacher.dto.request.TeacherLoginCommand

interface TeacherLoginUseCase {
    fun execute(command: TeacherLoginCommand): TokenResult
}
