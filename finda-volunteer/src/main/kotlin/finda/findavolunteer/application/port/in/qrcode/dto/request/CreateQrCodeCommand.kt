package finda.findavolunteer.application.port.`in`.qrcode.dto.request

import java.util.*

data class CreateQrCodeCommand(
    val volunteerId: UUID
)
