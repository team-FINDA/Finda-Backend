package finda.findaauth.application.service.teacher

import finda.findaauth.adapter.`in`.teacher.dto.request.TeacherSignupRequest
import finda.findaauth.application.exception.auth.InvalidPreAuthTokenException
import finda.findaauth.application.exception.mail.EmailNotVerifiedException
import finda.findaauth.application.exception.user.EmailAlreadyExistsException
import finda.findaauth.application.port.`in`.teacher.TeacherSignupUseCase
import finda.findaauth.application.port.out.teacher.TeacherPreAuthQueryPort
import finda.findaauth.application.port.out.teacher.TeacherCommandPort
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
    private val passwordEncoder: PasswordEncoder
) : TeacherSignupUseCase {

    override fun execute(
        preAuthToken: String,
        request: TeacherSignupRequest
    ) {
        if (!teacherPreAuthQueryPort.isValid(preAuthToken)) {
            throw InvalidPreAuthTokenException
        }

        val email = request.email

        if (!verificationStore.isVerified(email)) {
            throw EmailNotVerifiedException
        }

        if (userQueryPort.existsByEmail(email)) {
            throw EmailAlreadyExistsException
        }

        val user = User(
            id = null,
            email = email,
            name = request.name,
            password = passwordEncoder.encode(request.password)
        )

        val savedUser = userCommandPort.save(user)

        val teacher = Teacher(
            id = null,
            userId = savedUser.id!!
        )

        teacherCommandPort.save(teacher)
    }
}
