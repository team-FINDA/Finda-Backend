package finda.findaauth.adapter.`in`.student.dto.response

import finda.findaauth.application.port.`in`.student.dto.response.GetStudentsResult
import java.util.UUID

data class GetStudentsResponse(
    val students: List<StudentResponse>
) {
    data class StudentResponse(
        val id: UUID,
        val gcn: Int,
        val name: String
    )

    companion object {
        fun from(result: GetStudentsResult) = GetStudentsResponse(
            students = result.students.map {
                StudentResponse(
                    id = it.id,
                    gcn = it.gcn,
                    name = it.name
                )
            }
        )
    }
}
