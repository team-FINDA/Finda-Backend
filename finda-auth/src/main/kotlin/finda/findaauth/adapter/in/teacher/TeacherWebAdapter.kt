package finda.findaauth.adapter.`in`.teacher

import finda.findaauth.adapter.`in`.auth.dto.response.EmailVerificationWebResponse
import finda.findaauth.adapter.`in`.auth.dto.response.TokenWebResponse
import finda.findaauth.adapter.`in`.teacher.dto.request.SendEmailVerificationWebRequest
import finda.findaauth.adapter.`in`.teacher.dto.request.TeacherLoginWebRequest
import finda.findaauth.adapter.`in`.teacher.dto.request.TeacherSignupWebRequest
import finda.findaauth.adapter.`in`.teacher.dto.request.VerifyEmailCodeWebRequest
import finda.findaauth.adapter.`in`.teacher.dto.request.VerifySignupWebRequest
import finda.findaauth.adapter.`in`.teacher.dto.response.PreAuthTokenWebResponse
import finda.findaauth.adapter.`in`.teacher.dto.response.TeacherProfileResponse
import finda.findaauth.application.port.`in`.teacher.GetTeacherProfileUseCase
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
import finda.findaauth.application.service.user.UserFacade
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "교사", description = "교사 회원가입, 로그인, 이메일 인증, 가입 검증 API")
@RestController
@RequestMapping("/teachers")
class TeacherWebAdapter(
    private val verifyTeacherSignupUseCase: VerifyTeacherSignupUseCase,
    private val sendEmailVerificationUseCase: SendEmailVerificationUseCase,
    private val verifyEmailCodeUseCase: VerifyEmailCodeUseCase,
    private val teacherSignupUseCase: TeacherSignupUseCase,
    private val teacherLoginUseCase: TeacherLoginUseCase,
    private val getTeacherProfileUseCase: GetTeacherProfileUseCase,
    private val userFacade: UserFacade
) {

    @Operation(summary = "교사 가입 시크릿 검증", description = "교사 회원가입 시크릿 키를 검증하고 pre-auth 토큰을 발급합니다.")
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

    @Operation(summary = "교사 이메일 인증 코드 발송", description = "pre-auth 토큰과 함께 교사 이메일로 인증 코드를 발송합니다.")
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

    @Operation(summary = "교사 이메일 인증 코드 확인", description = "발송된 인증 코드의 유효성을 검증합니다.")
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

    @Operation(summary = "교사 회원가입", description = "이메일 인증 완료 후 교사 계정을 생성합니다.")
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

    @Operation(summary = "교사 로그인", description = "이메일과 비밀번호로 로그인하여 Access/Refresh 토큰을 발급받습니다.")
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

    @Operation(summary = "내 프로필 조회", description = "현재 로그인한 교사의 프로필 정보를 반환합니다.")
    @GetMapping("/me")
    fun getMyProfile(): TeacherProfileResponse {
        val userId = userFacade.getCurrentUserId()
        return TeacherProfileResponse.from(getTeacherProfileUseCase.getProfile(userId))
    }
}
