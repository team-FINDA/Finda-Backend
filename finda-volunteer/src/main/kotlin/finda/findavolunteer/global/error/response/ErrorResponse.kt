package finda.findavolunteer.global.error.response

import finda.findavolunteer.global.error.exception.ErrorCode

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
