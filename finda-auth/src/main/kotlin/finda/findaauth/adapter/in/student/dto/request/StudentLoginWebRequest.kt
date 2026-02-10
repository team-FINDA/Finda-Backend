package finda.findaauth.adapter.`in`.student.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class StudentLoginWebRequest(

    @field:NotBlank(message = "accountId는 필수입니다")
    val accountId: String,

    @field:NotBlank(message = "비밀번호는 필수입니다")
    @field:Size(min = 8, message = "비밀번호는 최소 8자입니다")
    val password: String
)
