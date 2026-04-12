package finda.findavolunteer.adapter.out.persistence.participation

import finda.findavolunteer.adapter.out.persistence.participation.mapper.TeacherParticipationMapper
import finda.findavolunteer.adapter.out.persistence.participation.repository.TeacherParticipationRepository
import finda.findavolunteer.application.exception.participation.TeacherParticipationNotFoundException
import finda.findavolunteer.application.port.out.participation.TeacherParticipationCommandPort
import finda.findavolunteer.application.port.out.participation.TeacherParticipationQueryPort
import finda.findavolunteer.domain.participation.model.TeacherParticipation
import org.springframework.stereotype.Component
import java.util.*

@Component
class TeacherParticipationAdapter(
    private val teacherParticipationRepository: TeacherParticipationRepository,
    private val teacherParticipationMapper: TeacherParticipationMapper
) : TeacherParticipationCommandPort, TeacherParticipationQueryPort {
    override fun save(teacherParticipation: TeacherParticipation): TeacherParticipation {
        val entity = teacherParticipationRepository.save(teacherParticipationMapper.toEntity(teacherParticipation))
        return teacherParticipationMapper.toDomain(entity)
    }

    override fun findByUserId(userId: UUID): TeacherParticipation? {
        val entity = teacherParticipationRepository.findAllByUserId(userId).firstOrNull()
        return entity?.let(teacherParticipationMapper::toDomain)
    }

    override fun findByUserIdOrThrow(userId: UUID): TeacherParticipation {
        return findByUserId(userId) ?: throw TeacherParticipationNotFoundException
    }
}
