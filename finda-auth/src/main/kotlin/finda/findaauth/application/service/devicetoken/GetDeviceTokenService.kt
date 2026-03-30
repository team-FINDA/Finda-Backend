package finda.findaauth.application.service.devicetoken

import finda.findaauth.application.exception.devicetoken.DeviceTokenNotFoundException
import finda.findaauth.application.port.out.devicetoken.DeviceTokenQueryPort
import finda.findaauth.domain.devicetoken.model.DeviceToken
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GetDeviceTokenService(
    private val deviceTokenQueryPort: DeviceTokenQueryPort
) {

    fun getByUserId(userId: UUID): DeviceToken {
        return deviceTokenQueryPort.findByUserId(userId)
            ?: throw DeviceTokenNotFoundException
    }

    fun getAllByUserIds(userIds: List<UUID>): List<DeviceToken> {
        if (userIds.isEmpty()) return emptyList()

        return deviceTokenQueryPort.findAllByUserIds(userIds)
    }
}
