package finda.findaauth.application.port.out.teacher

import finda.findaauth.domain.teacher.model.Teacher
import java.util.UUID

interface TeacherQueryPort {
    fun existsByUserId(userId: UUID): Boolean
    fun findTeacherByUserId(userId: UUID): Teacher?
    fun findAllByUserIds(userIds: List<UUID>): List<Teacher?>
    fun findAll(): List<Teacher>
}
