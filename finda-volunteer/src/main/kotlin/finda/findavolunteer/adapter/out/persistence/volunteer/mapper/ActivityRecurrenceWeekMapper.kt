package finda.findavolunteer.adapter.out.persistence.volunteer.mapper

import finda.findavolunteer.adapter.out.persistence.GenericMapper
import finda.findavolunteer.adapter.out.persistence.volunteer.entity.recurrence.ActivityRecurrenceWeekJpaEntity
import finda.findavolunteer.adapter.out.persistence.volunteer.repository.VolunteerRepository
import finda.findavolunteer.domain.volunteer.model.recurrence.ActivityRecurrenceWeek
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ActivityRecurrenceWeekMapper(
    private val volunteerRepository: VolunteerRepository
) : GenericMapper<ActivityRecurrenceWeek, ActivityRecurrenceWeekJpaEntity> {

    override fun toDomain(entity: ActivityRecurrenceWeekJpaEntity): ActivityRecurrenceWeek {
        return ActivityRecurrenceWeek(
            id = entity.id!!,
            weekday = entity.weekday,
            volunteerId = entity.volunteer!!.id!!
        )
    }

    override fun toEntity(domain: ActivityRecurrenceWeek): ActivityRecurrenceWeekJpaEntity {
        val volunteer = volunteerRepository.findByIdOrNull(domain.volunteerId)

        return ActivityRecurrenceWeekJpaEntity(
            id = domain.id,
            weekday = domain.weekday,
            volunteer = volunteer
        )
    }
}
