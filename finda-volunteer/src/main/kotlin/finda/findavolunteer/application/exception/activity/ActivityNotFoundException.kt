package finda.findavolunteer.application.exception.activity

import finda.findavolunteer.global.error.exception.ErrorCode
import finda.findavolunteer.global.error.exception.FindaException

object ActivityNotFoundException : FindaException(
    ErrorCode.ACTIVITY_NOT_FOUND
)
