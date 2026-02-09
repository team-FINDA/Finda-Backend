package finda.findaauth.application.service.student

import finda.findaauth.adapter.`in`.student.dto.request.StudentLoginRequest
import finda.findaauth.adapter.`in`.auth.dto.response.TokenResponse
import finda.findaauth.application.port.`in`.student.StudentLoginUseCase
import finda.findaauth.application.port.out.user.UserQueryPort
import finda.findaauth.domain.user.model.UserType
import finda.findaauth.global.security.jwt.JwtTokenProvider
import finda.findaauth.global.security.jwt.exception.UnauthorizedException
import finda.findaauth.global.util.StudentEmailUtils
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class StudentLoginService(
    private val userQueryPort: UserQueryPort,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) : StudentLoginUseCase {

    override fun execute(request: StudentLoginRequest): TokenResponse {
        val email = StudentEmailUtils.toFullEmail(request.accountId)

        val user = userQueryPort.findByEmail(email)
            ?: throw UnauthorizedException

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw UnauthorizedException
        }

        return jwtTokenProvider.generateTokens(user.id!!, UserType.STUDENT)
    }
}
