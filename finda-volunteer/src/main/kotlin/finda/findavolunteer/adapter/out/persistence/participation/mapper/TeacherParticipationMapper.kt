package finda.findavolunteer.adapter.out.persistence.participation.mapper

import finda.findavolunteer.adapter.out.persistence.GenericMapper
import finda.findavolunteer.adapter.out.persistence.participation.entity.TeacherParticipationJpaEntity
import finda.findavolunteer.adapter.out.persistence.volunteer.mapper.VolunteerMapper
import finda.findavolunteer.application.port.out.volunteer.VolunteerQueryPort
import finda.findavolunteer.domain.participation.model.TeacherParticipation
import org.springframework.stereotype.Component

@Component
class TeacherParticipationMapper(
    private val volunteerQueryPort: VolunteerQueryPort,
    private val volunteerMapper: VolunteerMapper
) : GenericMapper<TeacherParticipation, TeacherParticipationJpaEntity> {

    override fun toDomain(entity: TeacherParticipationJpaEntity): TeacherParticipation {
        return TeacherParticipation(
            id = entity.id!!,
            userId = entity.userId,
            volunteerId = entity.volunteer.id!!
        )
    }

    override fun toEntity(domain: TeacherParticipation): TeacherParticipationJpaEntity {
        val volunteer = volunteerMapper.toEntity(volunteerQueryPort.findByIdOrThrow(domain.volunteerId))

        return TeacherParticipationJpaEntity(
            id = domain.id,
            userId = domain.userId,
            volunteer = volunteer
        )
    }
}
