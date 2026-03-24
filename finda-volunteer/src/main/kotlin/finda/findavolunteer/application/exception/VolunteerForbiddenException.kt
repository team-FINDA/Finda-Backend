package finda.findavolunteer.application.exception

import finda.findavolunteer.global.error.exception.ErrorCode
import finda.findavolunteer.global.error.exception.FindaException

object VolunteerForbiddenException: FindaException (
    ErrorCode.VOLUNTEER_FORBIDDEN
)