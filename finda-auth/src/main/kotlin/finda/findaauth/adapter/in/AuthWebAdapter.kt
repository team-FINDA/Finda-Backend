package finda.findaauth.adapter.`in`

import finda.findaauth.adapter.`in`.dto.request.VerifyTeacherSignupRequest
import finda.findaauth.adapter.`in`.dto.response.PreAuthTokenResponse
import finda.findaauth.application.port.`in`.auth.VerifyTeacherSignupUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthWebAdapter(
    private val verifyTeacherSignupUseCase: VerifyTeacherSignupUseCase
) {
    @PostMapping("/verify")
    fun verifyTeacherSignup(
        @RequestBody request: VerifyTeacherSignupRequest
    ): PreAuthTokenResponse {
        return verifyTeacherSignupUseCase.execute(request)
    }
}
