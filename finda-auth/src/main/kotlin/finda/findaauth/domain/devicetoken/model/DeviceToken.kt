package finda.findaauth.domain.devicetoken.model

import finda.findaauth.domain.devicetoken.enum.DeviceOs
import java.util.UUID

data class DeviceToken(
    val id: UUID,
    val userId: UUID,
    val deviceToken: String,
    val os: DeviceOs
)
