package finda.findavolunteer.adapter.out.persistence.activity.mapper

import finda.findavolunteer.adapter.out.persistence.GenericMapper
import finda.findavolunteer.adapter.out.persistence.activity.entity.UserActivityJpaEntity
import finda.findavolunteer.adapter.out.persistence.activity.repository.ActivityRepository
import finda.findavolunteer.domain.activity.model.UserActivity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class UserActivityMapper(
    private val activityRepository: ActivityRepository
) : GenericMapper<UserActivity, UserActivityJpaEntity> {

    override fun toDomain(entity: UserActivityJpaEntity?): UserActivity? {
        return entity?.let {
            UserActivity(
                id = it.id!!,
                activityId = it.activity!!.id!!
            )
        }
    }

    override fun toEntity(domain: UserActivity): UserActivityJpaEntity {
        val activity = activityRepository.findByIdOrNull(domain.activityId)

        return UserActivityJpaEntity(
            id = domain.id,
            activity = activity
        )
    }
}
