package finda.findaauth.application.port.`in`.teacher

import finda.findaauth.adapter.`in`.teacher.dto.request.VerifySignupRequest
import finda.findaauth.adapter.`in`.teacher.dto.response.PreAuthTokenResponse

interface VerifyTeacherSignupUseCase {
    fun execute(request: VerifySignupRequest): PreAuthTokenResponse
}
