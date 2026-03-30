package finda.findaauth.application.port.out.devicetoken

import finda.findaauth.domain.devicetoken.model.DeviceToken
import java.util.UUID

interface DeviceTokenQueryPort {
    fun findByUserId(userId: UUID): DeviceToken?
    fun findAllByUserIds(userIds: List<UUID>): List<DeviceToken>
}
