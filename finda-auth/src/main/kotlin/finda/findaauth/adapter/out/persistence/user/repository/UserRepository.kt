package finda.findaauth.adapter.out.persistence.user.repository

import finda.findaauth.adapter.out.persistence.user.entity.UserJpaEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : CrudRepository<UserJpaEntity, UUID>
