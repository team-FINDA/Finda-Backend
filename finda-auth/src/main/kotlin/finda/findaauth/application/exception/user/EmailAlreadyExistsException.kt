package finda.findaauth.application.exception.user

import finda.findaauth.global.error.exception.ErrorCode
import finda.findaauth.global.error.exception.FindaException

object EmailAlreadyExistsException : FindaException(ErrorCode.EMAIL_ALREADY_EXISTS)
