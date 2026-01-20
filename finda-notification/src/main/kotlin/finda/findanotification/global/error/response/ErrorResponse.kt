package finda.findanotification.global.error.response

import finda.findanotification.global.error.exception.ErrorCode

class ErrorResponse(
    val status: Int,
    val message: String

) {
    companion object {
        fun of(errorCode: ErrorCode, message: String): ErrorResponse {
            return ErrorResponse(
                status = errorCode.status(),
                message = message
            )
        }
    }
}
