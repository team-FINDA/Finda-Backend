package finda.findavolunteer.adapter.out.persistence.volunteer.mapper

import finda.findavolunteer.adapter.out.persistence.GenericMapper
import finda.findavolunteer.adapter.out.persistence.volunteer.entity.VolunteerScheduleJpaEntity
import finda.findavolunteer.adapter.out.persistence.volunteer.repository.VolunteerRepository
import finda.findavolunteer.domain.volunteer.model.VolunteerSchedule
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class VolunteerScheduleMapper(
    private val volunteerRepository: VolunteerRepository,
) : GenericMapper<VolunteerSchedule, VolunteerScheduleJpaEntity> {

    override fun toDomain(entity: VolunteerScheduleJpaEntity?): VolunteerSchedule? {
        return entity?.let {
            VolunteerSchedule(
                id = it.id!!,
                startDate = it.startDate,
                endDate = it.endDate,
                volunteerId = it.volunteer!!.id!!
            )
        }
    }

    override fun toEntity(domain: VolunteerSchedule): VolunteerScheduleJpaEntity {
        val volunteer = volunteerRepository.findByIdOrNull(domain.volunteerId)

        return VolunteerScheduleJpaEntity(
            id = domain.id,
            startDate = domain.startDate,
            endDate = domain.endDate,
            volunteer = volunteer,
        )
    }
}
