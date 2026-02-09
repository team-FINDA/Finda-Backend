package finda.findaauth.adapter.out.persistence.student

import finda.findaauth.adapter.out.persistence.student.mapper.StudentMapper
import finda.findaauth.adapter.out.persistence.student.repository.StudentRepository
import finda.findaauth.adapter.out.persistence.user.repository.UserRepository
import finda.findaauth.application.port.out.student.StudentCommandPort
import finda.findaauth.domain.student.model.Student
import org.springframework.stereotype.Component

@Component
class StudentPersistenceAdapter(
    private val userRepository: UserRepository,
    private val studentRepository: StudentRepository,
    private val studentMapper: StudentMapper
) : StudentCommandPort {

    override fun save(student: Student): Student {
        val userEntity = userRepository.getReferenceById(student.userId)

        val entity = studentMapper.toEntity(student, userEntity)

        val savedEntity = studentRepository.save(entity)
        return studentMapper.toDomain(savedEntity)
    }
}
