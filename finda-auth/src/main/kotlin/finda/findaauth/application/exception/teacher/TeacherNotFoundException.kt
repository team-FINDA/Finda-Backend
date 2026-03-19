package finda.findaauth.application.exception.teacher

import finda.error.ErrorCode
import finda.error.FindaException

object TeacherNotFoundException : FindaException(
    ErrorCode.TEACHER_NOT_FOUND
)
