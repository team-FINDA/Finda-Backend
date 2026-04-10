package finda.findaauth.application.port.out.student

import finda.findaauth.domain.student.model.Student
import java.util.UUID

interface StudentQueryPort {
    fun existsByUserId(userId: UUID): Boolean

    fun existsByStudentNumber(grade: Int, classNum: Int, num: Int): Boolean

    fun findStudentByUserId(userId: UUID): Student?

    fun findAllByUserIds(userIds: List<UUID>): List<Student?>

    fun findAll(): List<Student>

    fun findAllByGradeAndClassNum(grade: Int?, classNum: Int?): List<Student>

    fun findNameByUserId(userId: UUID): String?
}
