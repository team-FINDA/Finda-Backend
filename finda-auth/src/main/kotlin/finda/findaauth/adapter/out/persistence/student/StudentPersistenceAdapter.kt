package finda.findaauth.adapter.out.persistence.student

import finda.findaauth.adapter.out.persistence.student.mapper.StudentMapper
import finda.findaauth.adapter.out.persistence.student.repository.StudentRepository
import finda.findaauth.adapter.out.persistence.user.repository.UserRepository
import finda.findaauth.application.port.out.student.StudentCommandPort
import finda.findaauth.application.port.out.student.StudentQueryPort
import finda.findaauth.domain.student.model.Student
import org.springframework.stereotype.Component
import java.util.*

@Component
class StudentPersistenceAdapter(
    private val userRepository: UserRepository,
    private val studentRepository: StudentRepository,
    private val studentMapper: StudentMapper
) : StudentCommandPort, StudentQueryPort {

    override fun save(student: Student): Student {
        val userEntity = userRepository.getReferenceById(student.userId)

        val entity = studentMapper.toEntity(student, userEntity)

        val saved = studentRepository.save(entity)
        return studentMapper.toDomain(saved)
    }

    override fun existsByUserId(userId: UUID): Boolean {
        return studentRepository.existsByUserId(userId)
    }
}
