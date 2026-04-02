package finda.findavolunteer.application.exception

import finda.findavolunteer.global.error.exception.ErrorCode
import finda.findavolunteer.global.error.exception.FindaException

object InvalidVolunteerSortByException : FindaException(
    ErrorCode.INVALID_VOLUNTEER_SORT_BY
)
