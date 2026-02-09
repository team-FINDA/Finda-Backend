package finda.findaauth.adapter.out.persistence.student.mapper

import finda.findaauth.adapter.out.persistence.GenericMapper
import finda.findaauth.adapter.out.persistence.student.entity.StudentJpaEntity
import finda.findaauth.adapter.out.persistence.user.repository.UserRepository
import finda.findaauth.application.exception.user.UserNotFoundException
import finda.findaauth.domain.student.model.Student
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class StudentMapper(
    private val userRepository: UserRepository
) : GenericMapper<Student, StudentJpaEntity> {

    override fun toDomain(entity: StudentJpaEntity): Student =
        Student(
            id = entity.id!!,
            userId = entity.user!!.id!!,
            grade = entity.grade,
            classNum = entity.classNum,
            num = entity.num,
            totalVolunteerTime = entity.totalVolunteerTime,
            deletedAt = entity.deletedAt
        )

    override fun toEntity(domain: Student): StudentJpaEntity {
        val user = userRepository.findByIdOrNull(domain.userId)
            ?: throw UserNotFoundException

        return StudentJpaEntity(
            user = user,
            grade = domain.grade,
            classNum = domain.classNum,
            num = domain.num,
            totalVolunteerTime = domain.totalVolunteerTime,
            deletedAt = domain.deletedAt
        )
    }
}
