package finda.findaauth.application.port.`in`.devicetoken.dto.request

import finda.findaauth.domain.devicetoken.enum.DeviceOs
import java.util.UUID

data class RegisterDeviceTokenCommand(
    val userId: UUID,
    val deviceToken: String,
    val os: DeviceOs
)
