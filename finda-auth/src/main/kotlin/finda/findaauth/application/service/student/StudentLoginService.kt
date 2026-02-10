package finda.findaauth.application.service.student

import finda.findaauth.application.exception.auth.InvalidCredentialsException
import finda.findaauth.application.port.`in`.auth.dto.response.TokenResult
import finda.findaauth.application.port.`in`.student.StudentLoginUseCase
import finda.findaauth.application.port.`in`.student.dto.request.StudentLoginCommand
import finda.findaauth.application.port.out.student.StudentQueryPort
import finda.findaauth.application.port.out.user.UserQueryPort
import finda.findaauth.domain.user.model.UserType
import finda.findaauth.global.mail.util.StudentEmailUtils
import finda.findaauth.global.security.jwt.JwtTokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class StudentLoginService(
    private val userQueryPort: UserQueryPort,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val studentQueryPort: StudentQueryPort
) : StudentLoginUseCase {

    override fun execute(command: StudentLoginCommand): TokenResult {
        val email = StudentEmailUtils.toFullEmail(command.accountId)

        val user = userQueryPort.findByEmail(email)
            ?: throw InvalidCredentialsException

        if (!passwordEncoder.matches(command.password, user.password)) {
            throw InvalidCredentialsException
        }

        if (!studentQueryPort.existsByUserId(user.id!!)) {
            throw InvalidCredentialsException
        }

        return jwtTokenProvider.generateTokens(user.id, UserType.STUDENT)
    }
}
