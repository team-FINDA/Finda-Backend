package finda.findaauth.application.port.`in`.teacher

import finda.findaauth.application.port.`in`.teacher.dto.request.VerifySignupCommand
import finda.findaauth.application.port.`in`.teacher.dto.response.PreAuthTokenResult

interface VerifyTeacherSignupUseCase {
    fun execute(command: VerifySignupCommand): PreAuthTokenResult
}
