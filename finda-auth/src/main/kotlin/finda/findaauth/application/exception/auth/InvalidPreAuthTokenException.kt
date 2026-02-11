package finda.findaauth.application.exception.auth

import finda.findaauth.global.error.exception.ErrorCode
import finda.findaauth.global.error.exception.FindaException

object InvalidPreAuthTokenException : FindaException(ErrorCode.INVALID_PRE_AUTH_TOKEN)
