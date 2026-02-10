package finda.findaauth.application.service.teacher

import finda.findaauth.application.exception.auth.InvalidCredentialsException
import finda.findaauth.application.port.`in`.auth.dto.response.TokenResult
import finda.findaauth.application.port.`in`.teacher.TeacherLoginUseCase
import finda.findaauth.application.port.`in`.teacher.dto.request.TeacherLoginCommand
import finda.findaauth.application.port.out.teacher.TeacherQueryPort
import finda.findaauth.application.port.out.user.UserQueryPort
import finda.findaauth.domain.user.model.UserType
import finda.findaauth.global.security.jwt.JwtTokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class TeacherLoginService(
    private val userQueryPort: UserQueryPort,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val teacherQueryPort: TeacherQueryPort
) : TeacherLoginUseCase {

    override fun execute(command: TeacherLoginCommand): TokenResult {
        val user = userQueryPort.findByEmail(command.email)
            ?: throw InvalidCredentialsException

        if (!passwordEncoder.matches(command.password, user.password)) {
            throw InvalidCredentialsException
        }

        if (!teacherQueryPort.existsByUserId(user.id!!)) {
            throw InvalidCredentialsException
        }

        return jwtTokenProvider.generateTokens(user.id!!, UserType.TEACHER)
    }
}
