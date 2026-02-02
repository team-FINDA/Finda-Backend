package finda.findavolunteer.adapter.out.persistence.activity.repository

import finda.findavolunteer.adapter.out.persistence.activity.entity.ActivityJpaEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ActivityRepository : CrudRepository<ActivityJpaEntity, UUID>
