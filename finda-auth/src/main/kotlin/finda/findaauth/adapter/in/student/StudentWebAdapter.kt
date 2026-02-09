package finda.findaauth.adapter.`in`.student

import finda.findaauth.adapter.`in`.auth.dto.response.EmailVerificationResponse
import finda.findaauth.adapter.`in`.auth.dto.response.TokenResponse
import finda.findaauth.adapter.`in`.student.dto.request.SendEmailVerificationRequest
import finda.findaauth.adapter.`in`.student.dto.request.StudentLoginRequest
import finda.findaauth.adapter.`in`.student.dto.request.StudentSignupRequest
import finda.findaauth.adapter.`in`.student.dto.request.VerifyEmailCodeRequest
import finda.findaauth.application.port.`in`.student.SendEmailVerificationUseCase
import finda.findaauth.application.port.`in`.student.StudentLoginUseCase
import finda.findaauth.application.port.`in`.student.StudentSignupUseCase
import finda.findaauth.application.port.`in`.student.VerifyEmailCodeUseCase
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/students")
class StudentWebAdapter(
    private val sendEmailVerificationUseCase: SendEmailVerificationUseCase,
    private val verifyEmailCodeUseCase: VerifyEmailCodeUseCase,
    private val studentSignupUseCase: StudentSignupUseCase,
    private val studentLoginUseCase: StudentLoginUseCase
) {

    @PostMapping("/send-verification")
    fun sendEmailVerification(
        @Valid @RequestBody
        request: SendEmailVerificationRequest
    ): EmailVerificationResponse {
        return sendEmailVerificationUseCase.execute(request)
    }

    @PostMapping("/verify-email")
    fun verifyEmailCode(
        @Valid @RequestBody
        request: VerifyEmailCodeRequest
    ): EmailVerificationResponse {
        return verifyEmailCodeUseCase.execute(request)
    }

    @PostMapping("/signup")
    fun signup(
        @Valid @RequestBody
        request: StudentSignupRequest
    ) {
        return studentSignupUseCase.execute(request)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody @Valid
        request: StudentLoginRequest
    ): TokenResponse {
        return studentLoginUseCase.execute(request)
    }
}
