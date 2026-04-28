package finda.findaauth.adapter.`in`.devicetoken.dto.request

import finda.findaauth.domain.devicetoken.enum.DeviceOs
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class RegisterDeviceTokenWebRequest(
    @field:NotBlank
    val deviceToken: String,

    @field:NotNull
    var os: DeviceOs
)
