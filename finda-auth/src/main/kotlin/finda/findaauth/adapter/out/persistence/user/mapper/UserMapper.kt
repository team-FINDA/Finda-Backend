package finda.findaauth.adapter.out.persistence.user.mapper

import finda.findaauth.adapter.out.persistence.GenericMapper
import finda.findaauth.adapter.out.persistence.user.entity.UserJpaEntity
import finda.findaauth.domain.user.model.User
import org.springframework.stereotype.Component

@Component
class UserMapper : GenericMapper<User, UserJpaEntity> {

    override fun toDomain(entity: UserJpaEntity): User =
        User(
            id = entity.id!!,
            email = entity.email,
            name = entity.name,
            password = entity.password
        )

    override fun toEntity(domain: User): UserJpaEntity =
        UserJpaEntity(
            name = domain.name,
            email = domain.email,
            password = domain.password
        )
}
