package finda.findaauth.application.exception.teacher

import finda.findaauth.global.error.exception.ErrorCode
import finda.findaauth.global.error.exception.FindaException

object EmailNotVerifiedException : FindaException(ErrorCode.EMAIL_NOT_VERIFIED)
