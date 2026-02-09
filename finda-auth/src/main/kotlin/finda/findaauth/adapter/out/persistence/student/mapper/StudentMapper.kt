package finda.findaauth.adapter.out.persistence.student.mapper

import finda.findaauth.adapter.out.persistence.student.entity.StudentJpaEntity
import finda.findaauth.adapter.out.persistence.user.entity.UserJpaEntity
import finda.findaauth.domain.student.model.Student
import org.springframework.stereotype.Component

@Component
class StudentMapper {

    fun toDomain(entity: StudentJpaEntity): Student =
        Student(
            id = entity.id!!,
            userId = entity.user!!.id!!,
            grade = entity.grade,
            classNum = entity.classNum,
            num = entity.num,
            totalVolunteerTime = entity.totalVolunteerTime,
            deletedAt = entity.deletedAt
        )

    fun toEntity(domain: Student, user: UserJpaEntity): StudentJpaEntity =
        StudentJpaEntity(
            user = user,
            grade = domain.grade,
            classNum = domain.classNum,
            num = domain.num,
            totalVolunteerTime = domain.totalVolunteerTime,
            deletedAt = domain.deletedAt
        )
}
