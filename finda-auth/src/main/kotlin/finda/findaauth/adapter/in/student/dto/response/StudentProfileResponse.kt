package finda.findaauth.adapter.`in`.student.dto.response

import finda.findaauth.application.port.`in`.student.dto.response.StudentProfileResult

data class StudentProfileResponse(
    val name: String,
    val topActivities: List<String>
) {
    companion object {
        fun from(result: StudentProfileResult) = StudentProfileResponse(
            name = result.name,
            topActivities = result.topActivities
        )
    }
}
