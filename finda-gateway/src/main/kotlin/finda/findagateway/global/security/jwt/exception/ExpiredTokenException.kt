package finda.findagateway.global.security.jwt.exception

import finda.findagateway.global.error.exception.ErrorCode
import finda.findagateway.global.error.exception.FindaException

object ExpiredTokenException : FindaException(
    ErrorCode.EXPIRED_TOKEN
)
