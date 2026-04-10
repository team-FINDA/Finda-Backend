package finda.findaauth.application.port.`in`.teacher

import finda.findaauth.application.port.`in`.teacher.dto.response.TeacherProfileResult
import java.util.UUID

interface GetTeacherProfileUseCase {
    fun getProfile(id: UUID): TeacherProfileResult
}
