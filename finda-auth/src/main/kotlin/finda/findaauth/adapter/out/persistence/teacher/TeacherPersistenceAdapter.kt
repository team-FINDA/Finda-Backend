package finda.findaauth.adapter.out.persistence.teacher

import finda.findaauth.adapter.out.persistence.teacher.entity.TeacherJpaEntity
import finda.findaauth.adapter.out.persistence.teacher.mapper.TeacherMapper
import finda.findaauth.adapter.out.persistence.teacher.repository.TeacherRepository
import finda.findaauth.adapter.out.persistence.user.repository.UserRepository
import finda.findaauth.application.port.out.teacher.TeacherCommandPort
import finda.findaauth.domain.teacher.model.Teacher
import org.springframework.stereotype.Component

@Component
class TeacherPersistenceAdapter(
    private val teacherRepository: TeacherRepository,
    private val userRepository: UserRepository,
    private val teacherMapper: TeacherMapper
) : TeacherCommandPort {

    override fun save(teacher: Teacher): Teacher {
        val userEntity = userRepository.getReferenceById(teacher.userId)

        val entity = TeacherJpaEntity(
            user = userEntity
        )

        val saved = teacherRepository.save(entity)
        return teacherMapper.toDomain(saved)
    }
}
