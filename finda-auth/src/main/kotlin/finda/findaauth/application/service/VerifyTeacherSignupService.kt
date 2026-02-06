package finda.findaauth.application.service

import finda.findaauth.adapter.`in`.dto.request.VerifyTeacherSignupRequest
import finda.findaauth.adapter.`in`.dto.response.PreAuthTokenResponse
import finda.findaauth.application.config.TeacherSignupProperties
import finda.findaauth.application.exception.auth.InvalidSignupSecretException
import finda.findaauth.application.port.`in`.auth.VerifyTeacherSignupUseCase
import finda.findaauth.application.port.out.TeacherSignupTokenPort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class VerifyTeacherSignupService(
    private val teacherSignupProperties: TeacherSignupProperties,
    private val teacherSignupTokenPort: TeacherSignupTokenPort
) : VerifyTeacherSignupUseCase {

    override fun execute(request: VerifyTeacherSignupRequest): PreAuthTokenResponse {
        if (teacherSignupProperties.signupSecret != request.secretKey) {
            throw InvalidSignupSecretException
        }

        val preAuthToken = UUID.randomUUID().toString()

        teacherSignupTokenPort.save(preAuthToken)

        return PreAuthTokenResponse(preAuthToken)
    }
}
