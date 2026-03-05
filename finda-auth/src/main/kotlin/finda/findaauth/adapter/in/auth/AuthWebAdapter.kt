package finda.findaauth.adapter.`in`.auth

import finda.findaauth.adapter.`in`.auth.dto.response.TokenWebResponse
import finda.findaauth.application.port.`in`.auth.ReissueUseCase
import jakarta.validation.constraints.NotBlank
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/auth")
class AuthWebAdapter(
    private val reissueUseCase: ReissueUseCase
) {
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

    @GetMapping("/test")
    fun test() = "test"
}
