package finda.findaauth.application.service.teacher

import finda.findaauth.application.exception.teacher.TeacherNotFoundException
import finda.findaauth.application.port.`in`.teacher.GetTeacherProfileUseCase
import finda.findaauth.application.port.`in`.teacher.dto.response.TeacherProfileResult
import finda.findaauth.application.port.out.teacher.TeacherQueryPort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GetTeacherProfileService(
    private val teacherQueryPort: TeacherQueryPort
) : GetTeacherProfileUseCase {

    override fun getProfile(id: UUID): TeacherProfileResult {
        val name = teacherQueryPort.findNameByUserId(id)
            ?: throw TeacherNotFoundException

        return TeacherProfileResult(name = name)
    }
}
