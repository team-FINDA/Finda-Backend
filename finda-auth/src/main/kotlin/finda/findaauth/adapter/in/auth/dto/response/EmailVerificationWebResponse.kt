package finda.findaauth.adapter.`in`.auth.dto.response

import finda.findaauth.application.port.`in`.auth.dto.response.EmailVerificationResult

data class EmailVerificationWebResponse(
    val success: Boolean,
    val message: String
) {
    companion object {
        fun from(result: EmailVerificationResult): EmailVerificationWebResponse {
            return EmailVerificationWebResponse(
                success = result.success,
                message = result.message
            )
        }
    }
}
