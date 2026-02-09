package finda.findaauth.application.service.teacher

import finda.findaauth.adapter.`in`.teacher.dto.request.TeacherLoginRequest
import finda.findaauth.adapter.`in`.teacher.dto.response.TokenResponse
import finda.findaauth.application.port.`in`.teacher.TeacherLoginUseCase
import finda.findaauth.application.port.out.user.UserQueryPort
import finda.findaauth.domain.user.model.UserType
import finda.findaauth.global.security.jwt.JwtTokenProvider
import finda.findaauth.global.security.jwt.exception.UnauthorizedException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class TeacherLoginService(
    private val userQueryPort: UserQueryPort,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) : TeacherLoginUseCase {

    override fun execute(request: TeacherLoginRequest): TokenResponse {
        val user = userQueryPort.findByEmail(request.email)
            ?: throw UnauthorizedException

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw UnauthorizedException
        }

        return jwtTokenProvider.generateTokens(user.id!!, UserType.TEACHER)
    }
}
