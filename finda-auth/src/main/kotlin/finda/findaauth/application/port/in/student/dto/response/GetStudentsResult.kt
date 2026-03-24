package finda.findaauth.application.port.`in`.student.dto.response

import finda.findaauth.domain.student.model.Student
import java.util.UUID

data class GetStudentsResult(
    val students: List<StudentResult>
) {
    data class StudentResult(
        val userId: UUID,
        val gcn: Int,
        val name: String
    )

    companion object {
        fun from(students: List<Student>) = GetStudentsResult(
            students = students.map { student ->
                StudentResult(
                    userId = student.userId,
                    gcn = student.grade * 1000 + student.classNum * 100 + student.num,
                    name = student.name
                )
            }
        )
    }
}
