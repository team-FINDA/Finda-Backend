package finda.findaauth.adapter.`in`.auth

import finda.findaauth.adapter.`in`.auth.dto.response.TokenWebResponse
import finda.findaauth.application.port.`in`.auth.ReissueUseCase
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthWebAdapter(
    private val reissueUseCase: ReissueUseCase
) {
    @PostMapping("/reissue")
    fun reissue(
        @Valid @RequestHeader
        token: String
    ): TokenWebResponse {
        return TokenWebResponse.from(
            reissueUseCase.execute(token)
        )
    }
}
