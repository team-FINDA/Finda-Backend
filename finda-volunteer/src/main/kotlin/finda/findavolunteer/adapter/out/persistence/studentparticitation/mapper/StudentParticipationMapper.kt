package finda.findavolunteer.adapter.out.persistence.studentparticitation.mapper

import finda.findavolunteer.adapter.out.persistence.GenericMapper
import finda.findavolunteer.adapter.out.persistence.studentparticitation.entity.StudentParticipationJpaEntity
import finda.findavolunteer.adapter.out.persistence.volunteer.mapper.VolunteerMapper
import finda.findavolunteer.adapter.out.persistence.volunteer.repository.VolunteerRepository
import finda.findavolunteer.domain.particitation.model.StudentParticipation
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class StudentParticipationMapper(
    private val volunteerRepository: VolunteerRepository,
) : GenericMapper<StudentParticipation, StudentParticipationJpaEntity> {

    override fun toDomain(entity: StudentParticipationJpaEntity?): StudentParticipation? {
        return entity?.let {
            StudentParticipation(
                id = it.id!!,
                volunteerId = it.volunteer!!.id!!,
                status = it.status,
                participatedAt = it.participatedAt,
                userId = it.userId,
            )
        }
    }

    override fun toEntity(domain: StudentParticipation): StudentParticipationJpaEntity {
        val volunteer = volunteerRepository.findByIdOrNull(domain.volunteerId)


        return StudentParticipationJpaEntity(
            id = domain.id,
            volunteer = volunteer,
            status = domain.status,
            participatedAt = domain.participatedAt,
            userId = domain.userId,
        )
    }
}
