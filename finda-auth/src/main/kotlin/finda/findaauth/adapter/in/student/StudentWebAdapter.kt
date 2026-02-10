package finda.findaauth.adapter.`in`.student

import finda.findaauth.adapter.`in`.auth.dto.response.EmailVerificationWebResponse
import finda.findaauth.adapter.`in`.auth.dto.response.TokenWebResponse
import finda.findaauth.adapter.`in`.student.dto.request.SendEmailVerificationWebRequest
import finda.findaauth.adapter.`in`.student.dto.request.StudentLoginWebRequest
import finda.findaauth.adapter.`in`.student.dto.request.StudentSignupWebRequest
import finda.findaauth.adapter.`in`.student.dto.request.VerifyEmailCodeWebRequest
import finda.findaauth.application.port.`in`.student.SendEmailVerificationUseCase
import finda.findaauth.application.port.`in`.student.StudentLoginUseCase
import finda.findaauth.application.port.`in`.student.StudentSignupUseCase
import finda.findaauth.application.port.`in`.student.VerifyEmailCodeUseCase
import finda.findaauth.application.port.`in`.student.dto.request.SendEmailVerificationCommand
import finda.findaauth.application.port.`in`.student.dto.request.StudentLoginCommand
import finda.findaauth.application.port.`in`.student.dto.request.StudentSignupCommand
import finda.findaauth.application.port.`in`.student.dto.request.VerifyEmailCodeCommand
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
        request: SendEmailVerificationWebRequest
    ): EmailVerificationWebResponse {
        return EmailVerificationWebResponse.from(
            sendEmailVerificationUseCase.execute(
                SendEmailVerificationCommand(
                    accountId = request.accountId
                )
            )
        )
    }

    @PostMapping("/verify-email")
    fun verifyEmailCode(
        @Valid @RequestBody
        request: VerifyEmailCodeWebRequest
    ): EmailVerificationWebResponse {
        return EmailVerificationWebResponse.from(
            verifyEmailCodeUseCase.execute(
                VerifyEmailCodeCommand(
                    accountId = request.accountId,
                    code = request.code
                )
            )
        )
    }

    @PostMapping("/signup")
    fun signup(
        @Valid @RequestBody
        request: StudentSignupWebRequest
    ) {
        studentSignupUseCase.execute(
            StudentSignupCommand(
                accountId = request.accountId,
                studentInfo = request.studentInfo,
                password = request.password
            )
        )
    }

    @PostMapping("/login")
    fun login(
        @RequestBody @Valid
        request: StudentLoginWebRequest
    ): TokenWebResponse {
        return TokenWebResponse.from(
            studentLoginUseCase.execute(
                StudentLoginCommand(
                    accountId = request.accountId,
                    password = request.password
                )
            )
        )
    }
}
