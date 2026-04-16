package finda.findavolunteer.domain.qrcode.model

import java.time.LocalDateTime
import java.util.UUID

data class QrCode(
    val id: UUID = UUID(0, 0),
    val volunteerId: UUID,
    val code: String,
    val generatedAt: LocalDateTime,
    val isUsed: Boolean = false,
    val usedAt: LocalDateTime? = null,
    val studentId: UUID? = null,
    val teacherId: UUID
){
    fun updateStudentId(studentId: UUID): QrCode{
        return this.copy(studentId = studentId, usedAt = LocalDateTime.now())
    }
}
