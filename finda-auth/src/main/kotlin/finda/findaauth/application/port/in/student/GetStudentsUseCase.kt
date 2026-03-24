package finda.findaauth.application.port.`in`.student

import finda.findaauth.application.port.`in`.student.dto.response.GetStudentsResult

interface GetStudentsUseCase {
    fun execute(grade: Int?, classNum: Int?): GetStudentsResult
}
