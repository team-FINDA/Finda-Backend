package finda.findagateway.global.security.jwt.exception

import finda.findagateway.global.error.exception.ErrorCode
import finda.findagateway.global.error.exception.FindaException

object InvalidTokenException : FindaException(
    ErrorCode.INVALID_TOKEN
)
