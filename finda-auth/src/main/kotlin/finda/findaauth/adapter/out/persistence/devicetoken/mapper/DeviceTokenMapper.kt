package finda.findaauth.adapter.out.persistence.devicetoken.mapper

import finda.findaauth.adapter.out.persistence.GenericMapper
import finda.findaauth.adapter.out.persistence.devicetoken.entity.DeviceTokenJpaEntity
import finda.findaauth.adapter.out.persistence.user.repository.UserRepository
import finda.findaauth.domain.devicetoken.model.DeviceToken
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class DeviceTokenMapper(
    private val userRepository: UserRepository
) : GenericMapper<DeviceToken, DeviceTokenJpaEntity> {

    override fun toDomain(entity: DeviceTokenJpaEntity?): DeviceToken? {
        return entity?.let {
            DeviceToken(
                id = it.id!!,
                userId = it.user!!.id!!,
                deviceToken = it.deviceToken,
                os = it.os
            )
        }
    }

    override fun toEntity(domain: DeviceToken): DeviceTokenJpaEntity {
        val user = userRepository.findByIdOrNull(domain.userId)

        return DeviceTokenJpaEntity(
            id = domain.id,
            user = user,
            deviceToken = domain.deviceToken,
            os = domain.os
        )
    }
}
