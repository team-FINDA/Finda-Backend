package finda.findavolunteer.adapter.out.persistence.participation

import finda.findavolunteer.adapter.out.persistence.participation.mapper.StudentParticipationMapper
import finda.findavolunteer.adapter.out.persistence.participation.repository.StudentParticipationRepository
import finda.findavolunteer.application.port.out.participation.StudentParticipationCommandPort
import finda.findavolunteer.domain.participation.model.StudentParticipation
import org.springframework.stereotype.Component

@Component
class StudentParticipationAdapter(
    private val studentParticipationRepository: StudentParticipationRepository,
    private val studentParticipationMapper: StudentParticipationMapper
) : StudentParticipationCommandPort {
    override fun save(studentParticipation: StudentParticipation): StudentParticipation {
        val entity = studentParticipationRepository.save(studentParticipationMapper.toEntity(studentParticipation))
        return studentParticipationMapper.toDomain(entity)
    }
}
