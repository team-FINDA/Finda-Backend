package finda.findavolunteer.adapter.out.persistence.activity

import finda.findavolunteer.adapter.out.persistence.activity.mapper.ActivityMapper
import finda.findavolunteer.adapter.out.persistence.activity.mapper.UserActivityMapper
import finda.findavolunteer.adapter.out.persistence.activity.repository.ActivityRepository
import finda.findavolunteer.adapter.out.persistence.activity.repository.UserActivityRepository
import finda.findavolunteer.application.port.out.activity.ActivityCommandPort
import finda.findavolunteer.domain.activity.model.Activity
import org.springframework.stereotype.Component

@Component
class ActivityPersistenceAdapter(
    val userActivityRepository: UserActivityRepository,
    val userActivityMapper: UserActivityMapper,
    val activityRepository: ActivityRepository,
    val activityMapper: ActivityMapper
) : ActivityCommandPort {
    override fun save(activity: Activity): Activity {
        val entity = activityRepository.save(activityMapper.toEntity(activity))
        return activityMapper.toDomain(entity)
    }
}
