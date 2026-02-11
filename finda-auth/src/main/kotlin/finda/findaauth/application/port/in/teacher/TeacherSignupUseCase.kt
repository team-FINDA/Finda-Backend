package finda.findaauth.application.port.`in`.teacher

import finda.findaauth.application.port.`in`.teacher.dto.request.TeacherSignupCommand

interface TeacherSignupUseCase {
    fun execute(command: TeacherSignupCommand)
}
