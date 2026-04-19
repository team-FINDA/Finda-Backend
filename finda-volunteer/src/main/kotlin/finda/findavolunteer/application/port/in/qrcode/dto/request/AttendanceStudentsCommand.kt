package finda.findavolunteer.application.port.`in`.qrcode.dto.request

import java.util.UUID

data class AttendanceStudentsCommand(
    val userIds: List<UUID>,
    val volunteerId: UUID
)
