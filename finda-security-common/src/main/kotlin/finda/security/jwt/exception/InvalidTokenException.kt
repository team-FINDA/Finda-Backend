package finda.security.jwt.exception

import finda.error.ErrorCode
import finda.error.FindaException

object InvalidTokenException : FindaException(
    ErrorCode.INVALID_TOKEN
)
