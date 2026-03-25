package finda.findavolunteer.adapter.out.persistence.participation.mapper

import finda.findavolunteer.adapter.out.persistence.GenericMapper
import finda.findavolunteer.adapter.out.persistence.participation.entity.StudentParticipationJpaEntity
import finda.findavolunteer.adapter.out.persistence.volunteer.repository.VolunteerRepository
import finda.findavolunteer.domain.participation.model.StudentParticipation
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class StudentParticipationMapper(
    private val volunteerRepository: VolunteerRepository
) : GenericMapper<StudentParticipation, StudentParticipationJpaEntity> {

    override fun toDomain(entity: StudentParticipationJpaEntity): StudentParticipation {
        return StudentParticipation(
            id = entity.id!!,
            volunteerId = entity.volunteer!!.id!!,
            status = entity.status,
            participatedAt = entity.participatedAt,
            userId = entity.userId
        )
    }

    override fun toEntity(domain: StudentParticipation): StudentParticipationJpaEntity {
        val volunteer = volunteerRepository.findByIdOrNull(domain.volunteerId)

        return StudentParticipationJpaEntity(
            id = domain.id,
            volunteer = volunteer,
            status = domain.status,
            participatedAt = domain.participatedAt,
            userId = domain.userId
        )
    }
}
