package finda.findaauth.application.service.devicetoken

import finda.findaauth.application.port.`in`.devicetoken.RegisterDeviceTokenUseCase
import finda.findaauth.application.port.`in`.devicetoken.dto.request.RegisterDeviceTokenCommand
import finda.findaauth.application.port.out.devicetoken.DeviceTokenCommandPort
import finda.findaauth.application.port.out.devicetoken.DeviceTokenQueryPort
import finda.findaauth.domain.devicetoken.model.DeviceToken
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RegisterDeviceTokenService(
    private val deviceTokenQueryPort: DeviceTokenQueryPort,
    private val deviceTokenCommandPort: DeviceTokenCommandPort
) : RegisterDeviceTokenUseCase {

    override fun execute(command: RegisterDeviceTokenCommand) {
        val existingToken = deviceTokenQueryPort.findByUserId(command.userId)

        val deviceToken = existingToken?.copy(
            deviceToken = command.deviceToken,
            os = command.os
        )
            ?: DeviceToken(
                userId = command.userId,
                deviceToken = command.deviceToken,
                os = command.os
            )

        deviceTokenCommandPort.save(deviceToken)
    }
}
