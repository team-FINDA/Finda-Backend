package finda.findavolunteer.adapter.out.persistence.participation

import finda.findavolunteer.adapter.out.persistence.participation.mapper.StudentParticipationMapper
import finda.findavolunteer.adapter.out.persistence.participation.repository.StudentParticipationRepository
import finda.findavolunteer.application.port.out.participation.StudentParticipationCommandPort
import finda.findavolunteer.application.port.out.participation.StudentParticipationQueryPort
import finda.findavolunteer.domain.participation.model.StudentParticipation
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class StudentParticipationAdapter(
    private val studentParticipationRepository: StudentParticipationRepository,
    private val studentParticipationMapper: StudentParticipationMapper
) : StudentParticipationCommandPort, StudentParticipationQueryPort {
    override fun save(studentParticipation: StudentParticipation): StudentParticipation {
        val entity = studentParticipationRepository.save(studentParticipationMapper.toEntity(studentParticipation))
        return studentParticipationMapper.toDomain(entity)
    }

    override fun findById(id: UUID): StudentParticipation? {
        val entity = studentParticipationRepository.findByIdOrNull(id)
        return entity?.let(studentParticipationMapper::toDomain)
    }

    override fun findByUserIdAndVolunteerId(userId: UUID, volunteerId: UUID): StudentParticipation? {
        val entity = studentParticipationRepository.findByUserIdAndVolunteer_Id(userId, volunteerId)
        return entity?.let { studentParticipationMapper.toDomain(entity) }
    }

    override fun existsByUserIdAndVolunteerId(userId: UUID, volunteerId: UUID) =
        studentParticipationRepository.existsByUserIdAndVolunteer_Id(userId, volunteerId)
}
