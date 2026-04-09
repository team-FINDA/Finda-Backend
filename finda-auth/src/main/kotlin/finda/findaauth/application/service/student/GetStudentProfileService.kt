package finda.findaauth.application.service.student

import finda.findaauth.application.exception.student.StudentNotFoundException
import finda.findaauth.application.port.`in`.student.GetStudentProfileUseCase
import finda.findaauth.application.port.`in`.student.dto.response.StudentProfileResult
import finda.findaauth.application.port.out.grpc.VolunteerGrpcPort
import finda.findaauth.application.port.out.student.StudentQueryPort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GetStudentProfileService(
    private val studentQueryPort: StudentQueryPort,
    private val volunteerGrpcPort: VolunteerGrpcPort
) : GetStudentProfileUseCase {

    override fun getProfile(userId: UUID): StudentProfileResult {
        val name = studentQueryPort.findNameByUserId(userId)
            ?: throw StudentNotFoundException
        val topActivities = volunteerGrpcPort.getTopActivities(userId)

        return StudentProfileResult(
            name = name,
            topActivities = topActivities
        )
    }
}
