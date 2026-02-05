package finda.findaauth.global.security.jwt.exception

import finda.findaauth.global.error.exception.ErrorCode
import finda.findaauth.global.error.exception.FindaException

object InvalidTokenException : FindaException(ErrorCode.INVALID_TOKEN)
