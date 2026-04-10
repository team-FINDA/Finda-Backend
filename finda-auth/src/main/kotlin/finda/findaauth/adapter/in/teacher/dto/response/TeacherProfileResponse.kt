package finda.findaauth.adapter.`in`.teacher.dto.response

import finda.findaauth.application.port.`in`.teacher.dto.response.TeacherProfileResult

data class TeacherProfileResponse(
    val name: String
) {
    companion object {
        fun from(result: TeacherProfileResult) = TeacherProfileResponse(
            name = result.name
        )
    }
}
