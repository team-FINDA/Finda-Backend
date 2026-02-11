package finda.findaauth.adapter.out.persistence.teacher.mapper

import finda.findaauth.adapter.out.persistence.teacher.entity.TeacherJpaEntity
import finda.findaauth.domain.teacher.model.Teacher
import org.springframework.stereotype.Component

@Component
class TeacherMapper {

    fun toDomain(entity: TeacherJpaEntity): Teacher =
        Teacher(
            id = entity.id!!,
            userId = entity.user!!.id!!
        )
}
