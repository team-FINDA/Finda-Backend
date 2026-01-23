package finda.findaauth.domain.teacher.model

import java.time.LocalDateTime
import java.util.UUID

data class Teacher(
    val id: UUID,
    val userId: UUID,
    val deletedAt: LocalDateTime? = null
)
