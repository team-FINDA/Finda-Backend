package finda.findaauth.domain.teacher.model

import java.util.UUID

data class Teacher(
    val id: UUID = UUID(0, 0),
    val userId: UUID
)
