package finda.findavolunteer.adapter.out.persistence.participation.mapper

import finda.findavolunteer.adapter.out.persistence.GenericMapper
import finda.findavolunteer.adapter.out.persistence.participation.entity.VolunteerTeachersJpaEntity
import finda.findavolunteer.adapter.out.persistence.volunteer.repository.VolunteerRepository
import finda.findavolunteer.domain.particitation.model.VolunteerTeachers
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class VolunteerTeachersMapper(
    private val volunteerRepository: VolunteerRepository
) : GenericMapper<VolunteerTeachers, VolunteerTeachersJpaEntity> {

    override fun toDomain(entity: VolunteerTeachersJpaEntity): VolunteerTeachers {
        return VolunteerTeachers(
            id = entity.id!!,
            userId = entity.userId,
            volunteerId = entity.volunteer!!.id!!
        )
    }

    override fun toEntity(domain: VolunteerTeachers): VolunteerTeachersJpaEntity {
        val volunteer = volunteerRepository.findByIdOrNull(domain.volunteerId)

        return VolunteerTeachersJpaEntity(
            id = domain.id,
            userId = domain.userId,
            volunteer = volunteer
        )
    }
}
