package finda.findaauth.application.exception.auth

import finda.findaauth.global.error.exception.ErrorCode
import finda.findaauth.global.error.exception.FindaException

object InvalidSignupSecretException : FindaException(ErrorCode.INVALID_SIGNUP_SECRET)
