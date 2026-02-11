package finda.findaauth.application.service.teacher

import finda.findaauth.application.exception.auth.InvalidPreAuthTokenException
import finda.findaauth.application.exception.mail.EmailNotVerifiedException
import finda.findaauth.application.exception.user.EmailAlreadyExistsException
import finda.findaauth.application.port.`in`.teacher.TeacherSignupUseCase
import finda.findaauth.application.port.`in`.teacher.dto.request.TeacherSignupCommand
import finda.findaauth.application.port.out.teacher.TeacherCommandPort
import finda.findaauth.application.port.out.teacher.TeacherPreAuthCommandPort
import finda.findaauth.application.port.out.teacher.TeacherPreAuthQueryPort
import finda.findaauth.application.port.out.user.UserCommandPort
import finda.findaauth.application.port.out.user.UserQueryPort
import finda.findaauth.domain.teacher.model.Teacher
import finda.findaauth.domain.user.model.User
import finda.findaauth.global.mail.EmailVerificationStore
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TeacherSignupService(
    private val verificationStore: EmailVerificationStore,
    private val teacherPreAuthQueryPort: TeacherPreAuthQueryPort,
    private val userCommandPort: UserCommandPort,
    private val teacherCommandPort: TeacherCommandPort,
    private val userQueryPort: UserQueryPort,
    private val passwordEncoder: PasswordEncoder,
    private val teacherPreAuthCommandPort: TeacherPreAuthCommandPort
) : TeacherSignupUseCase {

    override fun execute(
        command: TeacherSignupCommand
    ) {
        if (!teacherPreAuthQueryPort.isValid(command.preAuthToken)) {
            throw InvalidPreAuthTokenException
        }

        val email = command.email

        if (!verificationStore.isVerified(email)) {
            throw EmailNotVerifiedException
        }

        if (userQueryPort.existsByEmail(email)) {
            throw EmailAlreadyExistsException
        }

        val user = User(
            id = null,
            email = email,
            name = command.name,
            password = passwordEncoder.encode(command.password)
        )

        val savedUser = userCommandPort.save(user)

        teacherCommandPort.save(Teacher(id = null, userId = savedUser.id!!))

        teacherPreAuthCommandPort.delete(command.preAuthToken)
    }
}
