package finda.findavolunteer.application.exception.user

import finda.findavolunteer.global.error.exception.ErrorCode
import finda.findavolunteer.global.error.exception.FindaException

object UserNotFoundException : FindaException(
    ErrorCode.USER_NOT_FOUND
)
