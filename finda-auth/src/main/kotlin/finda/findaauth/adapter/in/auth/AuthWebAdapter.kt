package finda.findaauth.adapter.`in`.auth

import finda.findaauth.adapter.`in`.auth.dto.response.TokenWebResponse
import finda.findaauth.application.port.`in`.auth.ReissueUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.NotBlank
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "인증", description = "토큰 재발급 API")
@Validated
@RestController
@RequestMapping("/auth")
class AuthWebAdapter(
    private val reissueUseCase: ReissueUseCase
) {
    @Operation(summary = "토큰 재발급", description = "Refresh-Token 헤더로 새로운 Access/Refresh 토큰을 발급합니다.")
    @PostMapping("/reissue")
    fun reissue(
        @NotBlank
        @RequestHeader("Refresh-Token")
        token: String
    ): TokenWebResponse {
        return TokenWebResponse.from(
            reissueUseCase.execute(token)
        )
    }
}
