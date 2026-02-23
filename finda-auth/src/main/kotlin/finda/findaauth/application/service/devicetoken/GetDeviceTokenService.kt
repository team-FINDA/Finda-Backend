package finda.findaauth.application.service.devicetoken

import finda.findaauth.application.port.out.devicetoken.GetDeviceTokenPort
import finda.findaauth.domain.devicetoken.model.DeviceToken
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GetDeviceTokenService(
    private val getDeviceTokenPort: GetDeviceTokenPort
) {
    fun getByUserId(userId: UUID): DeviceToken =
        getDeviceTokenPort.getByUserId(userId)

    fun getAllByUserIds(userIds: List<UUID>): List<DeviceToken> =
        getDeviceTokenPort.getAllByUserIds(userIds)
}
