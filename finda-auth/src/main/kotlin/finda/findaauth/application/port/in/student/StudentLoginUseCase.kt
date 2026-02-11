package finda.findaauth.application.port.`in`.student

import finda.findaauth.application.port.`in`.auth.dto.response.TokenResult
import finda.findaauth.application.port.`in`.student.dto.request.StudentLoginCommand

interface StudentLoginUseCase {
    fun execute(command: StudentLoginCommand): TokenResult
}
