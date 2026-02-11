package finda.findaauth.adapter.out.persistence.user.repository

import finda.findaauth.adapter.out.persistence.user.entity.UserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : JpaRepository<UserJpaEntity, UUID> {
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): UserJpaEntity?
}
