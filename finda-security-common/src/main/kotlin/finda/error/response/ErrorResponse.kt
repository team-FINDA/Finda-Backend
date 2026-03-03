package finda.error.response

import finda.error.ErrorCode

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
