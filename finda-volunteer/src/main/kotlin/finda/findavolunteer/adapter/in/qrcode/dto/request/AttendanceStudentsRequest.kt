package finda.findavolunteer.adapter.`in`.qrcode.dto.request

import java.util.UUID

data class AttendanceStudentsRequest(
    val userIds: List<UUID>,
    val volunteerId: UUID
)
