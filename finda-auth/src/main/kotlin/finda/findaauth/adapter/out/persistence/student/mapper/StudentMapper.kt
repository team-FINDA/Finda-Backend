package finda.findaauth.adapter.out.persistence.student.mapper

import finda.findaauth.adapter.out.persistence.GenericMapper
import finda.findaauth.adapter.out.persistence.student.entity.StudentJpaEntity
import finda.findaauth.adapter.out.persistence.user.repository.UserRepository
import finda.findaauth.domain.student.model.Student
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class StudentMapper(
    private val userRepository: UserRepository
) : GenericMapper<Student, StudentJpaEntity> {

    override fun toDomain(entity: StudentJpaEntity?): Student? {
        return entity?.let {
            Student(
                id = it.id!!,
                userId = it.user!!.id!!,
                grade = it.grade,
                classNum = it.classNum,
                num = it.num,
                totalVolunteerTime = it.totalVolunteerTime,
                deletedAt = it.deletedAt
            )
        }
    }

    override fun toEntity(domain: Student): StudentJpaEntity {
        val user = userRepository.findByIdOrNull(domain.userId)

        return StudentJpaEntity(
            id = domain.id,
            user = user,
            grade = domain.grade,
            classNum = domain.classNum,
            num = domain.num,
            totalVolunteerTime = domain.totalVolunteerTime,
            deletedAt = domain.deletedAt
        )
    }
}
