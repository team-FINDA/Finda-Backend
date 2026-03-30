package finda.findavolunteer.adapter.out.persistence.activity

import finda.findavolunteer.adapter.out.persistence.activity.mapper.ActivityMapper
import finda.findavolunteer.adapter.out.persistence.activity.mapper.UserActivityMapper
import finda.findavolunteer.adapter.out.persistence.activity.repository.ActivityRepository
import finda.findavolunteer.adapter.out.persistence.activity.repository.UserActivityRepository
import finda.findavolunteer.application.port.out.activity.ActivityCommandPort
import finda.findavolunteer.application.port.out.activity.ActivityQueryPort
import finda.findavolunteer.domain.activity.model.Activity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ActivityPersistenceAdapter(
    val userActivityRepository: UserActivityRepository,
    val userActivityMapper: UserActivityMapper,
    val activityRepository: ActivityRepository,
    val activityMapper: ActivityMapper
) : ActivityCommandPort, ActivityQueryPort {
    override fun save(activity: Activity): Activity {
        val entity = activityRepository.save(activityMapper.toEntity(activity))
        return activityMapper.toDomain(entity)
    }

    override fun findById(id: UUID): Activity? {
        val entity = activityRepository.findByIdOrNull(id)
        return entity?.let(activityMapper::toDomain)
    }
}
