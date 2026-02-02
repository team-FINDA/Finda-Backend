package finda.findavolunteer.domain.qrcode.model

import java.time.LocalDateTime
import java.util.UUID

data class QrCode(
    val id: UUID,
    val volunteerId: UUID,
    val code: String,
    val generatedAt: LocalDateTime,
    val isUsed: Boolean = false,
    val usedAt: LocalDateTime? = null,
    val studentId: UUID,
    val teacherId: UUID
)
