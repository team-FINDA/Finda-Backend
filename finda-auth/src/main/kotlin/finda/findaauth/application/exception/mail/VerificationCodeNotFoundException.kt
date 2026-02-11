package finda.findaauth.application.exception.mail

import finda.findaauth.global.error.exception.ErrorCode
import finda.findaauth.global.error.exception.FindaException

object VerificationCodeNotFoundException : FindaException(ErrorCode.VERIFICATION_CODE_NOT_FOUND)
