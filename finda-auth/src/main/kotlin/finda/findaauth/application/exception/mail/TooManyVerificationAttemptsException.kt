package finda.findaauth.application.exception.mail

import finda.findaauth.global.error.exception.ErrorCode
import finda.findaauth.global.error.exception.FindaException

object TooManyVerificationAttemptsException : FindaException(ErrorCode.VERIFICATION_ATTEMPT_EXCEEDED)
