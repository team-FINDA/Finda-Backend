package finda.findaauth.adapter.`in`.devicetoken

import finda.findaauth.adapter.`in`.devicetoken.dto.request.RegisterDeviceTokenWebRequest
import finda.findaauth.application.port.`in`.devicetoken.RegisterDeviceTokenUseCase
import finda.findaauth.application.port.`in`.devicetoken.dto.request.RegisterDeviceTokenCommand
import finda.findaauth.global.security.principal.CustomUserDetails
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/device-tokens")
class DeviceTokenWebAdapter(
    private val registerDeviceTokenUseCase: RegisterDeviceTokenUseCase
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun registerDeviceToken(
        @AuthenticationPrincipal customUserDetails: CustomUserDetails,
        @RequestBody @Valid
        request: RegisterDeviceTokenWebRequest
    ) {
        registerDeviceTokenUseCase.execute(
            RegisterDeviceTokenCommand(
                userId = customUserDetails.user.id,
                deviceToken = request.deviceToken,
                os = request.os
            )
        )
    }
}
