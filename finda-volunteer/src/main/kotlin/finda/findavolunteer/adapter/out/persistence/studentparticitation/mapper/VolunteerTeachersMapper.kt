package finda.findavolunteer.adapter.out.persistence.studentparticitation.mapper

import finda.findavolunteer.adapter.out.persistence.GenericMapper
import finda.findavolunteer.adapter.out.persistence.studentparticitation.entity.VolunteerTeachersJpaEntity
import finda.findavolunteer.adapter.out.persistence.volunteer.repository.VolunteerRepository
import finda.findavolunteer.domain.particitation.model.VolunteerTeachers
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class VolunteerTeachersMapper(
    private val volunteerRepository: VolunteerRepository
) : GenericMapper<VolunteerTeachers, VolunteerTeachersJpaEntity> {

    override fun toDomain(entity: VolunteerTeachersJpaEntity?): VolunteerTeachers? {
        return entity?.let {
            VolunteerTeachers(
                id = it.id!!,
                userId = it.userId,
                volunteerId = it.volunteer!!.id!!
            )
        }
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
