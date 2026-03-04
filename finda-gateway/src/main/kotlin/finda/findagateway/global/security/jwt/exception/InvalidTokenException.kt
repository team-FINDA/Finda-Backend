package finda.findagateway.global.security.jwt.exception

import finda.findagateway.global.error.ErrorCode
import finda.findagateway.global.error.FindaException

object InvalidTokenException : FindaException(
    ErrorCode.INVALID_TOKEN
)
