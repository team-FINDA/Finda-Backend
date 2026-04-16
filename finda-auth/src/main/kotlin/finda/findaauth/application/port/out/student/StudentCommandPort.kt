package finda.findaauth.application.port.out.student

import finda.findaauth.domain.student.model.Student
import java.util.UUID

interface StudentCommandPort {
    fun save(student: Student): Student
    fun addVolunteerTime(userId: UUID, volunteerTime: Float)
}
