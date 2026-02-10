package finda.findaauth.application.service.student

import finda.findaauth.application.exception.mail.EmailNotVerifiedException
import finda.findaauth.application.exception.student.StudentNumberAlreadyExistsException
import finda.findaauth.application.exception.user.EmailAlreadyExistsException
import finda.findaauth.application.port.`in`.student.StudentSignupUseCase
import finda.findaauth.application.port.`in`.student.dto.request.StudentSignupCommand
import finda.findaauth.application.port.out.student.StudentCommandPort
import finda.findaauth.application.port.out.student.StudentQueryPort
import finda.findaauth.application.port.out.user.UserCommandPort
import finda.findaauth.application.port.out.user.UserQueryPort
import finda.findaauth.domain.student.model.Student
import finda.findaauth.domain.student.vo.StudentNumber
import finda.findaauth.domain.user.model.User
import finda.findaauth.global.mail.EmailVerificationStore
import finda.findaauth.global.mail.util.StudentEmailUtils
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class StudentSignupService(
    private val verificationStore: EmailVerificationStore,
    private val userCommandPort: UserCommandPort,
    private val studentCommandPort: StudentCommandPort,
    private val userQueryPort: UserQueryPort,
    private val studentQueryPort: StudentQueryPort,
    private val passwordEncoder: PasswordEncoder
) : StudentSignupUseCase {

    override fun execute(command: StudentSignupCommand) {
        val email = StudentEmailUtils.toFullEmail(command.accountId)
        val (studentNumber, name) = StudentNumber.parse(command.studentInfo)

        validateSignup(email, studentNumber)

        val user = userCommandPort.save(
            User(
                id = null,
                email = email,
                name = name,
                password = passwordEncoder.encode(command.password)
            )
        )

        val student = Student(
            id = null,
            userId = user.id!!,
            grade = studentNumber.grade,
            classNum = studentNumber.classNum,
            num = studentNumber.num
        )

        studentCommandPort.save(student)
    }

    private fun validateSignup(email: String, studentNumber: StudentNumber) { // 파라미터 추가
        if (!verificationStore.isVerified(email)) {
            throw EmailNotVerifiedException
        }

        if (userQueryPort.existsByEmail(email)) {
            throw EmailAlreadyExistsException
        }

        if (studentQueryPort.existsByStudentNumber(
                grade = studentNumber.grade,
                classNum = studentNumber.classNum,
                num = studentNumber.num
            )
        ) {
            throw StudentNumberAlreadyExistsException
        }
    }
}
