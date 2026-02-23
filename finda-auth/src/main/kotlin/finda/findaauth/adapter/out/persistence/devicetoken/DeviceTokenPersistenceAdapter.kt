package finda.findaauth.adapter.out.persistence.devicetoken

import finda.findaauth.adapter.out.persistence.devicetoken.mapper.DeviceTokenMapper
import finda.findaauth.adapter.out.persistence.devicetoken.repository.DeviceTokenRepository
import finda.findaauth.application.exception.devicetoken.DeviceTokenNotFoundException
import finda.findaauth.application.port.out.devicetoken.GetDeviceTokenPort
import finda.findaauth.domain.devicetoken.model.DeviceToken
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class DeviceTokenPersistenceAdapter(
    private val deviceTokenRepository: DeviceTokenRepository,
    private val deviceTokenMapper: DeviceTokenMapper
) : GetDeviceTokenPort {

    override fun getByUserId(userId: UUID): DeviceToken {
        val entity = deviceTokenRepository.findByUser_Id(userId)
            ?: throw DeviceTokenNotFoundException
        return deviceTokenMapper.toDomain(entity)
    }

    override fun getAllByUserIds(userIds: List<UUID>): List<DeviceToken> {
        return deviceTokenRepository.findAllByUser_IdIn(userIds)
            .map { deviceTokenMapper.toDomain(it) }
    }
}
