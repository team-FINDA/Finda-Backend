package finda.findaauth.domain.student.model
import java.util.UUID

data class Student(
    val id: UUID = UUID(0, 0),
    val userId: UUID,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val name: String,
    val totalVolunteerTime: Int = 0
)
