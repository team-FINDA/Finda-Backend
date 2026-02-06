package finda.findaauth.application.port.`in`.auth

import finda.findaauth.adapter.`in`.dto.request.VerifyTeacherSignupRequest
import finda.findaauth.adapter.`in`.dto.response.PreAuthTokenResponse

interface VerifyTeacherSignupUseCase {
    fun execute(request: VerifyTeacherSignupRequest): PreAuthTokenResponse
}
