package finda.findaauth.adapter.out.persistence.student

import finda.findaauth.adapter.out.persistence.student.mapper.StudentMapper
import finda.findaauth.adapter.out.persistence.student.repository.StudentRepository
import finda.findaauth.adapter.out.persistence.user.repository.UserRepository
import finda.findaauth.application.port.out.student.StudentCommandPort
import finda.findaauth.application.port.out.student.StudentQueryPort
import finda.findaauth.domain.student.model.Student
import org.springframework.data.repository.findByIdOrNull
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

    override fun existsByStudentNumber(grade: Int, classNum: Int, num: Int): Boolean {
        return studentRepository.existsByGradeAndClassNumAndNum(grade, classNum, num)
    }

    override fun findStudentByUserId(userId: UUID): Student? {
        val userEntity = userRepository.findByIdOrNull(userId) ?: return null
        val student = studentRepository.findByUser(userEntity).orElse(null) ?: return null
        return studentMapper.toDomain(student)
    }

    override fun findAllByUserIds(userIds: List<UUID>): List<Student?> {
        return userIds.map { findStudentByUserId(it) }
    }

    override fun findAll(): List<Student> {
        return studentRepository.findAll().map(studentMapper::toDomain).toList()
    }

    override fun findAllByGradeAndClassNum(grade: Int?, classNum: Int?): List<Student> {
        return when {
            grade != null && classNum != null ->
                studentRepository.findAllByGradeAndClassNum(grade, classNum)
            grade != null ->
                studentRepository.findAllByGrade(grade)
            else ->
                studentRepository.findAll()
        }.map(studentMapper::toDomain)
    }

    override fun findNameByUserId(userId: UUID): String? {
        val userEntity = userRepository.findByIdOrNull(userId) ?: return null
        return userEntity.name
    }
}
