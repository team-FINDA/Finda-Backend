package finda.findavolunteer.adapter.out.persistence.volunteer

import finda.findavolunteer.adapter.out.persistence.volunteer.mapper.ActivityRecurrenceMonthMapper
import finda.findavolunteer.adapter.out.persistence.volunteer.mapper.ActivityRecurrenceWeekMapper
import finda.findavolunteer.adapter.out.persistence.volunteer.mapper.VolunteerMapper
import finda.findavolunteer.adapter.out.persistence.volunteer.repository.ActivityRecurrenceMonthRepository
import finda.findavolunteer.adapter.out.persistence.volunteer.repository.ActivityRecurrenceWeekRepository
import finda.findavolunteer.adapter.out.persistence.volunteer.repository.VolunteerRepository
import finda.findavolunteer.application.exception.VolunteerNotFoundException
import finda.findavolunteer.application.port.out.volunteer.VolunteerCommandPort
import finda.findavolunteer.application.port.out.volunteer.VolunteerQueryPort
import finda.findavolunteer.domain.volunteer.model.Volunteer
import finda.findavolunteer.domain.volunteer.model.recurrence.ActivityRecurrenceMonth
import finda.findavolunteer.domain.volunteer.model.recurrence.ActivityRecurrenceWeek
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Component
class VolunteerPersistenceAdapter(
    val volunteerRepository: VolunteerRepository,
    val volunteerMapper: VolunteerMapper,
    val activityRecurrenceMonthRepository: ActivityRecurrenceMonthRepository,
    val activityRecurrenceMonthMapper: ActivityRecurrenceMonthMapper,
    val activityRecurrenceWeekRepository: ActivityRecurrenceWeekRepository,
    val activityRecurrenceWeekMapper: ActivityRecurrenceWeekMapper
) : VolunteerCommandPort, VolunteerQueryPort {
    override fun save(volunteer: Volunteer): Volunteer {
        val entity = volunteerRepository.save(volunteerMapper.toEntity(volunteer))
        return volunteerMapper.toDomain(entity)
    }

    override fun saveWeekRecurrence(activityRecurrenceWeek: ActivityRecurrenceWeek): ActivityRecurrenceWeek {
        val entity = activityRecurrenceWeekRepository.save(activityRecurrenceWeekMapper.toEntity(activityRecurrenceWeek))
        return activityRecurrenceWeekMapper.toDomain(entity)
    }

    override fun saveMonthRecurrence(activityRecurrenceMonth: ActivityRecurrenceMonth): ActivityRecurrenceMonth {
        val entity = activityRecurrenceMonthRepository.save(activityRecurrenceMonthMapper.toEntity(activityRecurrenceMonth))
        return activityRecurrenceMonthMapper.toDomain(entity)
    }

    override fun findByUserId(userId: UUID): List<Volunteer> {
        TODO("Not yet implemented")
    }

    override fun findById(id: UUID): Volunteer? {
        val entity = volunteerRepository.findByIdOrNull(id)
        return entity?.let{ volunteerMapper.toDomain(entity) }
    }

    override fun findByIdOrThrow(id: UUID): Volunteer {
        return findById(id) ?: throw VolunteerNotFoundException
    }

    override fun deleteById(volunteerId: UUID) {
        volunteerRepository.deleteById(volunteerId)
    }
}
