package finda.findaauth.application.service.student

import finda.findaauth.application.port.`in`.student.AddVolunteerTimeUseCase
import finda.findaauth.application.port.out.student.StudentCommandPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class AddVolunteerTimeService(
    private val studentCommandPort: StudentCommandPort
) : AddVolunteerTimeUseCase {

    @Transactional
    override fun execute(userId: UUID, volunteerTime: Float) {
        studentCommandPort.addVolunteerTime(userId, volunteerTime)
    }
}
