package finda.findaauth.application.service.teacher

import finda.findaauth.adapter.`in`.teacher.dto.request.VerifySignupRequest
import finda.findaauth.adapter.`in`.teacher.dto.response.PreAuthTokenResponse
import finda.findaauth.application.config.TeacherSignupProperties
import finda.findaauth.application.exception.auth.InvalidSignupSecretException
import finda.findaauth.application.port.`in`.teacher.VerifyTeacherSignupUseCase
import finda.findaauth.application.port.out.auth.AuthCommandPort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class VerifyTeacherSignupService(
    private val teacherSignupProperties: TeacherSignupProperties,
    private val authCommandPort: AuthCommandPort
) : VerifyTeacherSignupUseCase {

    override fun execute(request: VerifySignupRequest): PreAuthTokenResponse {
        if (teacherSignupProperties.signupSecret != request.secretKey) {
            throw InvalidSignupSecretException
        }

        val preAuthToken = UUID.randomUUID().toString()

        authCommandPort.save(preAuthToken)

        return PreAuthTokenResponse(preAuthToken)
    }
}
