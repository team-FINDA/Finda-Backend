package finda.findavolunteer.adapter.out.persistence.activity.repository

import finda.findavolunteer.adapter.out.persistence.activity.entity.UserActivityJpaEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserActivityRepository : CrudRepository<UserActivityJpaEntity, UUID>
