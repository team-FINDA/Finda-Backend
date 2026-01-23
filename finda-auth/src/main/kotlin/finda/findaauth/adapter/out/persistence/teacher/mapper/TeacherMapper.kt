package finda.findaauth.adapter.out.persistence.teacher.mapper

import finda.findaauth.adapter.out.persistence.GenericMapper
import finda.findaauth.adapter.out.persistence.teacher.entity.TeacherJpaEntity
import finda.findaauth.adapter.out.persistence.user.repository.UserRepository
import finda.findaauth.domain.teacher.model.Teacher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class TeacherMapper(
    private val userRepository: UserRepository
) : GenericMapper<Teacher, TeacherJpaEntity> {

    override fun toDomain(entity: TeacherJpaEntity?): Teacher? {
        return entity?.let {
            Teacher(
                id = it.id!!,
                userId = it.user!!.id!!
            )
        }
    }

    override fun toEntity(domain: Teacher): TeacherJpaEntity {
        val user = userRepository.findByIdOrNull(domain.userId)
        return TeacherJpaEntity(
            id = domain.id,
            user = user
        )
    }
}
