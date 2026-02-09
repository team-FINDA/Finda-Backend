package finda.findaauth.application.exception.teacher

import finda.findaauth.global.error.exception.ErrorCode
import finda.findaauth.global.error.exception.FindaException

object VerificationCodeMismatchException : FindaException(ErrorCode.VERIFICATION_CODE_MISMATCH)
