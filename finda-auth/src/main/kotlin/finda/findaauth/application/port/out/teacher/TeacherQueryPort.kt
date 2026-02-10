package finda.findaauth.application.port.out.teacher

import java.util.UUID

interface TeacherQueryPort {
    fun existsByUserId(userId: UUID): Boolean
}
