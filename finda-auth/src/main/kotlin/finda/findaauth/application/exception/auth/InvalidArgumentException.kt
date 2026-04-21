package finda.findaauth.application.exception.auth

import finda.error.ErrorCode
import finda.error.FindaException

object InvalidArgumentException : FindaException(
    ErrorCode.INVALID_ARGUMENT
)
