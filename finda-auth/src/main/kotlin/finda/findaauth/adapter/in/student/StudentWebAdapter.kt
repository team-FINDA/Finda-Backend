package finda.findaauth.adapter.`in`.student

import finda.findaauth.adapter.`in`.auth.dto.response.EmailVerificationWebResponse
import finda.findaauth.adapter.`in`.auth.dto.response.TokenWebResponse
import finda.findaauth.adapter.`in`.student.dto.request.SendEmailVerificationWebRequest
import finda.findaauth.adapter.`in`.student.dto.request.StudentLoginWebRequest
import finda.findaauth.adapter.`in`.student.dto.request.StudentSignupWebRequest
import finda.findaauth.adapter.`in`.student.dto.request.VerifyEmailCodeWebRequest
import finda.findaauth.adapter.`in`.student.dto.response.GetStudentsResponse
import finda.findaauth.adapter.`in`.student.dto.response.StudentProfileResponse
import finda.findaauth.application.port.`in`.student.GetStudentProfileUseCase
import finda.findaauth.application.port.`in`.student.GetStudentsUseCase
import finda.findaauth.application.port.`in`.student.SendEmailVerificationUseCase
import finda.findaauth.application.port.`in`.student.StudentLoginUseCase
import finda.findaauth.application.port.`in`.student.StudentSignupUseCase
import finda.findaauth.application.port.`in`.student.VerifyEmailCodeUseCase
import finda.findaauth.application.port.`in`.student.dto.request.SendEmailVerificationCommand
import finda.findaauth.application.port.`in`.student.dto.request.StudentLoginCommand
import finda.findaauth.application.port.`in`.student.dto.request.StudentSignupCommand
import finda.findaauth.application.port.`in`.student.dto.request.VerifyEmailCodeCommand
import finda.findaauth.application.service.user.UserFacade
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/students")
class StudentWebAdapter(
    private val sendEmailVerificationUseCase: SendEmailVerificationUseCase,
    private val verifyEmailCodeUseCase: VerifyEmailCodeUseCase,
    private val studentSignupUseCase: StudentSignupUseCase,
    private val studentLoginUseCase: StudentLoginUseCase,
    private val getStudentsUseCase: GetStudentsUseCase,
    private val getStudentProfileUseCase: GetStudentProfileUseCase,
    private val userFacade: UserFacade
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

    @GetMapping
    fun getStudents(
        @RequestParam(required = false) grade: Int?,
        @RequestParam(required = false) classNum: Int?
    ): GetStudentsResponse {
        return GetStudentsResponse.from(
            getStudentsUseCase.execute(
                grade,
                classNum
            )
        )
    }

    @GetMapping("/me")
    fun getMyProfile(): StudentProfileResponse {
        val userId = userFacade.getCurrentUserId()
        return StudentProfileResponse.from(getStudentProfileUseCase.getProfile(userId))
    }
}
