package finda.findaauth.application.port.`in`.student

import finda.findaauth.adapter.`in`.student.dto.request.StudentSignupRequest

interface StudentSignupUseCase {
    fun execute(request: StudentSignupRequest)
}
