package finda.findaauth.application.port.out.devicetoken

import finda.findaauth.domain.devicetoken.model.DeviceToken
import java.util.UUID

interface GetDeviceTokenPort {
    fun getByUserId(userId: UUID): DeviceToken
    fun getAllByUserIds(userIds: List<UUID>): List<DeviceToken>
}
