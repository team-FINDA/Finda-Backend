package finda.findavolunteer.adapter.out.persistence.volunteer.mapper

import finda.findavolunteer.adapter.out.persistence.GenericMapper
import finda.findavolunteer.adapter.out.persistence.volunteer.entity.recurrence.ActivityRecurrenceYearJpaEntity
import finda.findavolunteer.adapter.out.persistence.volunteer.repository.VolunteerRepository
import finda.findavolunteer.domain.volunteer.model.recurrence.ActivityRecurrenceYear
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ActivityRecurrenceYearMapper(
    private val volunteerRepository: VolunteerRepository
) : GenericMapper<ActivityRecurrenceYear, ActivityRecurrenceYearJpaEntity> {

    override fun toDomain(entity: ActivityRecurrenceYearJpaEntity?): ActivityRecurrenceYear? {
        return entity?.let {
            ActivityRecurrenceYear(
                id = it.id!!,
                volunteerId = it.volunteer!!.id!!,
                day = it.day,
                month = it.month
            )
        }
    }

    override fun toEntity(domain: ActivityRecurrenceYear): ActivityRecurrenceYearJpaEntity {
        val volunteer = volunteerRepository.findByIdOrNull(domain.volunteerId)

        return ActivityRecurrenceYearJpaEntity(
            id = domain.id,
            volunteer = volunteer,
            day = domain.day,
            month = domain.month
        )
    }
}
