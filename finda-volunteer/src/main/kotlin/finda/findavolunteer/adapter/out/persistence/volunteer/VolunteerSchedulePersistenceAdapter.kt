package finda.findavolunteer.adapter.out.persistence.volunteer

import finda.findavolunteer.adapter.out.persistence.volunteer.mapper.VolunteerScheduleMapper
import finda.findavolunteer.adapter.out.persistence.volunteer.repository.VolunteerScheduleRepository
import finda.findavolunteer.application.port.out.volunteer.VolunteerScheduleCommandPort
import finda.findavolunteer.domain.volunteer.model.VolunteerSchedule
import org.springframework.stereotype.Component

@Component
class VolunteerSchedulePersistenceAdapter(
    val volunteerScheduleRepository: VolunteerScheduleRepository,
    val volunteerScheduleMapper: VolunteerScheduleMapper
) : VolunteerScheduleCommandPort {
    override fun save(volunteerSchedule: VolunteerSchedule): VolunteerSchedule {
        val entity = volunteerScheduleRepository.save(volunteerScheduleMapper.toEntity(volunteerSchedule))
        return volunteerScheduleMapper.toDomain(entity)
    }
}
