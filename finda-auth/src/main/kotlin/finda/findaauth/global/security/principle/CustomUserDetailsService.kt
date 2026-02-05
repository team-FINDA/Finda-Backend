package finda.findaauth.global.security.principle

import finda.findaauth.adapter.out.persistence.student.repository.StudentRepository
import finda.findaauth.adapter.out.persistence.teacher.repository.TeacherRepository
import finda.findaauth.adapter.out.persistence.user.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository,
    private val studentRepository: StudentRepository,
    private val teacherRepository: TeacherRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User not found with email: $email")

        val isStudent = studentRepository.existsByUserId(user.id!!)
        val isTeacher = teacherRepository.existsByUserId(user.id)

        return CustomUserDetails(
            userId = user.id,
            username = user.name,
            password = user.password,
            isStudent = isStudent,
            isTeacher = isTeacher
        )
    }
}
