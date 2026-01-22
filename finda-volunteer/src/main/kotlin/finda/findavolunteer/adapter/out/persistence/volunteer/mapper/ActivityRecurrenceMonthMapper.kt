package finda.findavolunteer.adapter.out.persistence.volunteer.mapper

import finda.findavolunteer.adapter.out.persistence.GenericMapper
import finda.findavolunteer.adapter.out.persistence.volunteer.entity.recurrence.ActivityRecurrenceMonthJpaEntity
import finda.findavolunteer.adapter.out.persistence.volunteer.repository.VolunteerRepository
import finda.findavolunteer.domain.volunteer.model.recurrence.ActivityRecurrenceMonth
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ActivityRecurrenceMonthMapper(
    private val volunteerRepository: VolunteerRepository,
    private val volunteerMapper: VolunteerMapper,
) : GenericMapper<ActivityRecurrenceMonth, ActivityRecurrenceMonthJpaEntity> {

    override fun toDomain(entity: ActivityRecurrenceMonthJpaEntity?): ActivityRecurrenceMonth? {
        return entity?.let {
            ActivityRecurrenceMonth(
                id = it.id!!,
                volunteerId = it.volunteer!!.id!!,
                day = it.day,
            )
        }
    }

    override fun toEntity(domain: ActivityRecurrenceMonth): ActivityRecurrenceMonthJpaEntity {
        val volunteer = volunteerRepository.findByIdOrNull(domain.volunteerId)

        return ActivityRecurrenceMonthJpaEntity(
            id = domain.id,
            volunteer = volunteer,
            day = domain.day,
        )
    }
}
