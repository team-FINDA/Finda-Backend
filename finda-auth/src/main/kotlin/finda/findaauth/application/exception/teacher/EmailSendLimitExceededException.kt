package finda.findaauth.application.exception.teacher

import finda.findaauth.global.error.exception.ErrorCode
import finda.findaauth.global.error.exception.FindaException

object EmailSendLimitExceededException : FindaException(ErrorCode.EMAIL_SEND_LIMIT_EXCEEDED)
