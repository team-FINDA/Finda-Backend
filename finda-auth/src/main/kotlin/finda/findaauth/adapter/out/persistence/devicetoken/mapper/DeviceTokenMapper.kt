package finda.findaauth.adapter.out.persistence.devicetoken.mapper

import finda.findaauth.adapter.out.persistence.GenericMapper
import finda.findaauth.adapter.out.persistence.devicetoken.entity.DeviceTokenJpaEntity
import finda.findaauth.adapter.out.persistence.user.repository.UserRepository
import finda.findaauth.application.exception.user.UserNotFoundException
import finda.findaauth.domain.devicetoken.model.DeviceToken
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class DeviceTokenMapper(
    private val userRepository: UserRepository
) : GenericMapper<DeviceToken, DeviceTokenJpaEntity> {

    override fun toDomain(entity: DeviceTokenJpaEntity): DeviceToken =
        DeviceToken(
            id = entity.id!!,
            userId = entity.user!!.id!!,
            deviceToken = entity.deviceToken,
            os = entity.os
        )

    override fun toEntity(domain: DeviceToken): DeviceTokenJpaEntity {
        val user = userRepository.findByIdOrNull(domain.userId)
            ?: throw UserNotFoundException

        return DeviceTokenJpaEntity(
            user = user,
            deviceToken = domain.deviceToken,
            os = domain.os
        )
    }
}
