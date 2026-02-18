package finda.findaauth.application.port.`in`.auth

import finda.findaauth.application.port.`in`.auth.dto.response.TokenResult

interface ReissueUseCase {
    fun execute(token: String): TokenResult
}
