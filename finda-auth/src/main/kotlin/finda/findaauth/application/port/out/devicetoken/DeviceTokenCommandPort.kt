package finda.findaauth.application.port.out.devicetoken

import finda.findaauth.domain.devicetoken.model.DeviceToken

interface DeviceTokenCommandPort {
    fun save(deviceToken: DeviceToken): DeviceToken
}
