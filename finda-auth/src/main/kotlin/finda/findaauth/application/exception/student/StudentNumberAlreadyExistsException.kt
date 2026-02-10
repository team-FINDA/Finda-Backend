package finda.findaauth.application.exception.student

import finda.findaauth.global.error.exception.ErrorCode
import finda.findaauth.global.error.exception.FindaException

object StudentNumberAlreadyExistsException : FindaException(ErrorCode.STUDENT_NUMBER_ALREADY_EXISTS)
