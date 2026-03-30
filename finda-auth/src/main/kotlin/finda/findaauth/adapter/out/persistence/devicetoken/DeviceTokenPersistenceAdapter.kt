package finda.findaauth.adapter.out.persistence.devicetoken

import finda.findaauth.adapter.out.persistence.devicetoken.mapper.DeviceTokenMapper
import finda.findaauth.adapter.out.persistence.devicetoken.repository.DeviceTokenRepository
import finda.findaauth.application.port.out.devicetoken.DeviceTokenCommandPort
import finda.findaauth.application.port.out.devicetoken.DeviceTokenQueryPort
import finda.findaauth.application.port.out.devicetoken.GetDeviceTokenPort
import finda.findaauth.domain.devicetoken.model.DeviceToken
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class DeviceTokenPersistenceAdapter(
    private val deviceTokenRepository: DeviceTokenRepository,
    private val deviceTokenMapper: DeviceTokenMapper
) : GetDeviceTokenPort, DeviceTokenQueryPort, DeviceTokenCommandPort {

    override fun save(deviceToken: DeviceToken): DeviceToken {
        val entity = deviceTokenRepository.save(deviceTokenMapper.toEntity(deviceToken))
        return deviceTokenMapper.toDomain(entity)
    }

    override fun findByUserId(userId: UUID): DeviceToken? {
        return deviceTokenRepository.findByUser_Id(userId)
            ?.let(deviceTokenMapper::toDomain)
    }

    override fun findAllByUserIds(userIds: List<UUID>): List<DeviceToken> {
        if (userIds.isEmpty()) return emptyList()

        return deviceTokenRepository.findAllByUser_IdIn(userIds)
            .map(deviceTokenMapper::toDomain)
    }
}
