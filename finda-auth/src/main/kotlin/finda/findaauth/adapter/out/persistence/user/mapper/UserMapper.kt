package finda.findaauth.adapter.out.persistence.user.mapper

import finda.findaauth.adapter.out.persistence.GenericMapper
import finda.findaauth.adapter.out.persistence.user.entity.UserJpaEntity
import finda.findaauth.domain.user.model.User
import org.springframework.stereotype.Component

@Component
class UserMapper : GenericMapper<User, UserJpaEntity> {

    override fun toDomain(entity: UserJpaEntity?): User? {
        return entity?.let {
            User(
                id = it.id!!,
                name = it.name,
                email = it.email,
                password = it.password
            )
        }
    }

    override fun toEntity(domain: User): UserJpaEntity {
        return UserJpaEntity(
            id = domain.id,
            name = domain.name,
            email = domain.email,
            password = domain.password
        )
    }
}
