package finda.findaauth.application.exception.auth

import finda.findaauth.global.error.exception.ErrorCode
import finda.findaauth.global.error.exception.FindaException

object InvalidCredentialsException : FindaException(ErrorCode.INVALID_CREDENTIALS)
