package finda.findaauth.application.port.`in`.student

import finda.findaauth.application.port.`in`.student.dto.request.StudentSignupCommand

interface StudentSignupUseCase {
    fun execute(command: StudentSignupCommand)
}
