package finda.findaauth.domain.student.model
import java.time.LocalDateTime
import java.util.UUID

data class Student(
    val id: UUID,
    val userId: UUID,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val totalVolunteerTime: Int,
    val deletedAt: LocalDateTime? = null
)
