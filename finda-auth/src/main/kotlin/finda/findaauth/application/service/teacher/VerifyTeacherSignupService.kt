package finda.findaauth.application.service.teacher

import finda.findaauth.application.config.TeacherSignupProperties
import finda.findaauth.application.exception.auth.InvalidSignupSecretException
import finda.findaauth.application.port.`in`.teacher.VerifyTeacherSignupUseCase
import finda.findaauth.application.port.`in`.teacher.dto.request.VerifySignupCommand
import finda.findaauth.application.port.`in`.teacher.dto.response.PreAuthTokenResult
import finda.findaauth.application.port.out.teacher.TeacherPreAuthCommandPort
import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.util.UUID

@Service
class VerifyTeacherSignupService(
    private val teacherSignupProperties: TeacherSignupProperties,
    private val teacherPreAuthCommandPort: TeacherPreAuthCommandPort
) : VerifyTeacherSignupUseCase {

    override fun execute(command: VerifySignupCommand): PreAuthTokenResult {
        if (!MessageDigest.isEqual(
                teacherSignupProperties.signupSecret.toByteArray(),
                command.secretKey.toByteArray()
            )
        ) {
            throw InvalidSignupSecretException
        }

        val preAuthToken = UUID.randomUUID().toString()

        teacherPreAuthCommandPort.save(preAuthToken)

        return PreAuthTokenResult(preAuthToken)
    }
}
