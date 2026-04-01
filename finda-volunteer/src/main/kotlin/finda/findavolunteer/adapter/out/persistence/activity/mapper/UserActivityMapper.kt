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

    override fun toDomain(entity: UserActivityJpaEntity): UserActivity {
        return UserActivity(
            id = entity.id!!,
            activityId = requireNotNull(entity.activity?.id) {
                "UserActivity(${entity.id}) is missing activity reference"
            },
            userId = entity.userId
        )
    }

    override fun toEntity(domain: UserActivity): UserActivityJpaEntity {
        val activity = activityRepository.findByIdOrNull(domain.activityId)

        return UserActivityJpaEntity(
            id = domain.id,
            userId = domain.userId,
            activity = activity
        )
    }
}
