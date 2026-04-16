package finda.findavolunteer.application.port.`in`.qrcode.dto.request

import java.util.UUID

data class AttendanceStudentsCommand(
    val usersId: List<UUID>,
    val volunteerId: UUID
)
