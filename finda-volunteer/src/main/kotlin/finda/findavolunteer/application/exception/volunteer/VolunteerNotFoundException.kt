package finda.findavolunteer.application.exception.volunteer

import finda.findavolunteer.global.error.exception.ErrorCode
import finda.findavolunteer.global.error.exception.FindaException

object VolunteerNotFoundException : FindaException(
    ErrorCode.VOLUNTEER_NOT_FOUND
)
