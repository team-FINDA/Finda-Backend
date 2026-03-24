package finda.findaauth.adapter.`in`.student.dto.response

import finda.findaauth.application.port.`in`.student.dto.response.GetStudentsResult

data class GetStudentsResponse(
    val students: List<StudentResponse>
) {
    data class StudentResponse(
        val gcn: Int,
        val name: String
    )

    companion object {
        fun from(result: GetStudentsResult) = GetStudentsResponse(
            students = result.students.map {
                StudentResponse(
                    gcn = it.gcn,
                    name = it.name
                )
            }
        )
    }
}
