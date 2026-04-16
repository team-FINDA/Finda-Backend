package finda.findavolunteer.adapter.`in`.qrcode.dto.request

import java.util.UUID

data class AttendanceStudentsRequest(
    val usersId: List<UUID>,
    val volunteerId: UUID
)
