package finda.findagateway.global.security.jwt.exception

import finda.findagateway.global.error.ErrorCode
import finda.findagateway.global.error.FindaException

object ExpiredTokenException : FindaException(
    ErrorCode.EXPIRED_TOKEN
)
