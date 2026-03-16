package finda.findaauth.application.exception.student

import finda.error.ErrorCode
import finda.error.FindaException

object StudentNotFoundException : FindaException(
    ErrorCode.STUDENT_NOT_FOUND
)
