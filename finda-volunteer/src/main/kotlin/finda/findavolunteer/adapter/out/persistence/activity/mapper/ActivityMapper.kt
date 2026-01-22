package finda.findavolunteer.adapter.out.persistence.activity.mapper

import finda.findavolunteer.adapter.out.persistence.GenericMapper
import finda.findavolunteer.adapter.out.persistence.activity.entity.ActivityJpaEntity
import finda.findavolunteer.adapter.out.persistence.volunteer.repository.VolunteerRepository
import finda.findavolunteer.domain.activity.model.Activity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ActivityMapper(
    private val volunteerRepository: VolunteerRepository,
) : GenericMapper<Activity, ActivityJpaEntity> {

    override fun toDomain(entity: ActivityJpaEntity?): Activity? {
        return entity?.let {
            Activity(
                id = it.id!!,
                activityName = it.activityName,
                volunteerId = it.volunteer!!.id!!,
            )
        }
    }

    override fun toEntity(domain: Activity): ActivityJpaEntity {
        val volunteer = volunteerRepository.findByIdOrNull(domain.volunteerId)

        return ActivityJpaEntity(
            id = domain.id,
            activityName = domain.activityName,
            volunteer = volunteer,
        )
    }
}
