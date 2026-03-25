package finda.findavolunteer.adapter.out.persistence.participation

import finda.findavolunteer.adapter.out.persistence.participation.mapper.TeacherParticipationMapper
import finda.findavolunteer.adapter.out.persistence.participation.repository.TeacherParticipationRepository
import finda.findavolunteer.application.port.out.participation.TeacherParticipationCommandPort
import finda.findavolunteer.domain.participation.model.TeacherParticipation
import org.springframework.stereotype.Component

@Component
class TeacherParticipationAdapter(
    private val teacherParticipationRepository: TeacherParticipationRepository,
    private val teacherParticipationMapper: TeacherParticipationMapper
) : TeacherParticipationCommandPort {
    override fun save(teacherParticipation: TeacherParticipation): TeacherParticipation {
        val entity = teacherParticipationRepository.save(teacherParticipationMapper.toEntity(teacherParticipation))
        return teacherParticipationMapper.toDomain(entity)
    }
}
