package finda.findaauth.application.port.`in`.student

import finda.findaauth.application.port.`in`.student.dto.response.StudentProfileResult
import java.util.UUID

interface GetStudentProfileUseCase {
    fun getProfile(userId: UUID): StudentProfileResult
}
