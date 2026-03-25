package finda.findaauth.application.service.student

import finda.findaauth.application.port.`in`.student.GetStudentsUseCase
import finda.findaauth.application.port.`in`.student.dto.response.GetStudentsResult
import finda.findaauth.application.port.out.student.StudentQueryPort
import org.springframework.stereotype.Service

@Service
class GetStudentsService(
    private val studentQueryPort: StudentQueryPort
) : GetStudentsUseCase {

    override fun execute(grade: Int?, classNum: Int?): GetStudentsResult {
        val students = studentQueryPort.findAllByGradeAndClassNum(grade, classNum)
        return GetStudentsResult.from(students)
    }
}
