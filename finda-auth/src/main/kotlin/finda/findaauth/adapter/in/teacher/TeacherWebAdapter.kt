package finda.findaauth.adapter.`in`.teacher

import finda.findaauth.adapter.`in`.auth.dto.response.EmailVerificationResponse
import finda.findaauth.adapter.`in`.auth.dto.response.TokenResponse
import finda.findaauth.adapter.`in`.teacher.dto.request.SendEmailVerificationRequest
import finda.findaauth.adapter.`in`.teacher.dto.request.TeacherLoginRequest
import finda.findaauth.adapter.`in`.teacher.dto.request.TeacherSignupRequest
import finda.findaauth.adapter.`in`.teacher.dto.request.VerifyEmailCodeRequest
import finda.findaauth.adapter.`in`.teacher.dto.request.VerifySignupRequest
import finda.findaauth.adapter.`in`.teacher.dto.response.PreAuthTokenResponse
import finda.findaauth.application.port.`in`.teacher.SendEmailVerificationUseCase
import finda.findaauth.application.port.`in`.teacher.TeacherLoginUseCase
import finda.findaauth.application.port.`in`.teacher.TeacherSignupUseCase
import finda.findaauth.application.port.`in`.teacher.VerifyEmailCodeUseCase
import finda.findaauth.application.port.`in`.teacher.VerifyTeacherSignupUseCase
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/teachers")
class TeacherWebAdapter(
    private val verifyTeacherSignupUseCase: VerifyTeacherSignupUseCase,
    private val sendEmailVerificationUseCase: SendEmailVerificationUseCase,
    private val verifyEmailCodeUseCase: VerifyEmailCodeUseCase,
    private val teacherSignupUseCase: TeacherSignupUseCase,
    private val teacherLoginUseCase: TeacherLoginUseCase
) {

    @PostMapping("/verify")
    fun verifyTeacherSignup(
        @Valid @RequestBody
        request: VerifySignupRequest
    ): PreAuthTokenResponse {
        return verifyTeacherSignupUseCase.execute(request)
    }

    @PostMapping("/send-verification")
    fun sendEmailVerification(
        @RequestHeader preAuthToken: String,
        @Valid @RequestBody
        request: SendEmailVerificationRequest
    ): EmailVerificationResponse {
        return sendEmailVerificationUseCase.execute(preAuthToken, request)
    }

    @PostMapping("/verify-email")
    fun verifyEmailCode(
        @RequestHeader preAuthToken: String,
        @Valid @RequestBody
        request: VerifyEmailCodeRequest
    ): EmailVerificationResponse {
        return verifyEmailCodeUseCase.execute(preAuthToken, request)
    }

    @PostMapping("/signup")
    fun signup(
        @RequestHeader preAuthToken: String,
        @Valid @RequestBody
        request: TeacherSignupRequest
    ) {
        return teacherSignupUseCase.execute(preAuthToken, request)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody @Valid
        request: TeacherLoginRequest
    ): TokenResponse {
        return teacherLoginUseCase.execute(request)
    }
}
