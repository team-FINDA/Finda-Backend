package finda.findaauth.adapter.out.persistence.user

import finda.findaauth.adapter.out.persistence.user.mapper.UserMapper
import finda.findaauth.adapter.out.persistence.user.repository.UserRepository
import finda.findaauth.application.port.out.user.UserCommandPort
import finda.findaauth.application.port.out.user.UserQueryPort
import finda.findaauth.domain.user.model.User
import org.springframework.stereotype.Component

@Component
class UserPersistenceAdapter(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) : UserQueryPort, UserCommandPort {

    override fun save(user: User): User {
        val entity = userMapper.toEntity(user)
        val saved = userRepository.save(entity)
        return userMapper.toDomain(saved)
    }

    override fun existsByEmail(email: String): Boolean =
        userRepository.existsByEmail(email)

    override fun findByEmail(email: String): User? {
        val userEntity = userRepository.findByEmail(email)
        return userEntity?.let { userMapper.toDomain(it) }
    }
}
