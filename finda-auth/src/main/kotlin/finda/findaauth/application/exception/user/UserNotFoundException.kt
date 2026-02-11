package finda.findaauth.application.exception.user

import finda.findaauth.global.error.exception.ErrorCode
import finda.findaauth.global.error.exception.FindaException

object UserNotFoundException : FindaException(ErrorCode.USER_NOT_FOUND)
