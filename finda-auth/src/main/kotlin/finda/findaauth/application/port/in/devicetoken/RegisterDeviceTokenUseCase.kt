package finda.findaauth.application.port.`in`.devicetoken

import finda.findaauth.application.port.`in`.devicetoken.dto.request.RegisterDeviceTokenCommand

interface RegisterDeviceTokenUseCase {
    fun execute(command: RegisterDeviceTokenCommand)
}
