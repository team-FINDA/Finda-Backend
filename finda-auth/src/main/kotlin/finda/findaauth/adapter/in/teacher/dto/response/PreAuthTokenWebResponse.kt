package finda.findaauth.adapter.`in`.teacher.dto.response

import finda.findaauth.application.port.`in`.teacher.dto.response.PreAuthTokenResult

data class PreAuthTokenWebResponse(
    val preAuthToken: String
) {
    companion object {
        fun from(result: PreAuthTokenResult): PreAuthTokenWebResponse {
            return PreAuthTokenWebResponse(
                preAuthToken = result.preAuthToken
            )
        }
    }
}
