package finda.security.jwt.exception

import finda.error.ErrorCode
import finda.error.FindaException

object ExpiredTokenException : FindaException(
    ErrorCode.EXPIRED_TOKEN
)
