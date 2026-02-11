package finda.findaauth.adapter.`in`.teacher

import finda.findaauth.adapter.`in`.auth.dto.response.EmailVerificationWebResponse
import finda.findaauth.adapter.`in`.auth.dto.response.TokenWebResponse
import finda.findaauth.adapter.`in`.teacher.dto.request.SendEmailVerificationWebRequest
import finda.findaauth.adapter.`in`.teacher.dto.request.TeacherLoginWebRequest
import finda.findaauth.adapter.`in`.teacher.dto.request.TeacherSignupWebRequest
import finda.findaauth.adapter.`in`.teacher.dto.request.VerifyEmailCodeWebRequest
import finda.findaauth.adapter.`in`.teacher.dto.request.VerifySignupWebRequest
import finda.findaauth.adapter.`in`.teacher.dto.response.PreAuthTokenWebResponse
import finda.findaauth.application.port.`in`.teacher.SendEmailVerificationUseCase
import finda.findaauth.application.port.`in`.teacher.TeacherLoginUseCase
import finda.findaauth.application.port.`in`.teacher.TeacherSignupUseCase
import finda.findaauth.application.port.`in`.teacher.VerifyEmailCodeUseCase
import finda.findaauth.application.port.`in`.teacher.VerifyTeacherSignupUseCase
import finda.findaauth.application.port.`in`.teacher.dto.request.SendEmailVerificationCommand
import finda.findaauth.application.port.`in`.teacher.dto.request.TeacherLoginCommand
import finda.findaauth.application.port.`in`.teacher.dto.request.TeacherSignupCommand
import finda.findaauth.application.port.`in`.teacher.dto.request.VerifyEmailCodeCommand
import finda.findaauth.application.port.`in`.teacher.dto.request.VerifySignupCommand
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
        request: VerifySignupWebRequest
    ): PreAuthTokenWebResponse {
        return PreAuthTokenWebResponse.from(
            verifyTeacherSignupUseCase.execute(
                VerifySignupCommand(
                    request.secretKey
                )
            )
        )
    }

    @PostMapping("/send-verification")
    fun sendEmailVerification(
        @RequestHeader preAuthToken: String,
        @RequestBody @Valid
        request: SendEmailVerificationWebRequest
    ): EmailVerificationWebResponse {
        return EmailVerificationWebResponse.from(
            sendEmailVerificationUseCase.execute(
                SendEmailVerificationCommand(
                    preAuthToken = preAuthToken,
                    email = request.email
                )
            )
        )
    }

    @PostMapping("/verify-email")
    fun verifyEmailCode(
        @RequestHeader preAuthToken: String,
        @Valid @RequestBody
        request: VerifyEmailCodeWebRequest
    ): EmailVerificationWebResponse {
        return EmailVerificationWebResponse.from(
            verifyEmailCodeUseCase.execute(
                VerifyEmailCodeCommand(
                    preAuthToken = preAuthToken,
                    email = request.email,
                    code = request.code
                )
            )
        )
    }

    @PostMapping("/signup")
    fun signup(
        @RequestHeader preAuthToken: String,
        @Valid @RequestBody
        request: TeacherSignupWebRequest
    ) {
        return teacherSignupUseCase.execute(
            TeacherSignupCommand(
                preAuthToken = preAuthToken,
                email = request.email,
                name = request.name,
                password = request.password
            )
        )
    }

    @PostMapping("/login")
    fun login(
        @RequestBody @Valid
        request: TeacherLoginWebRequest
    ): TokenWebResponse {
        return TokenWebResponse.from(
            teacherLoginUseCase.execute(
                TeacherLoginCommand(
                    email = request.email,
                    password = request.password
                )
            )
        )
    }
}
