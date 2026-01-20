package finda.findabatch.global.error.response

import finda.findabatch.global.error.exception.ErrorCode

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
