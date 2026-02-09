package finda.findaauth.application.port.`in`.teacher

import finda.findaauth.adapter.`in`.teacher.dto.request.TeacherSignupRequest

interface TeacherSignupUseCase {
    fun execute(preAuthToken: String, request: TeacherSignupRequest)
}
