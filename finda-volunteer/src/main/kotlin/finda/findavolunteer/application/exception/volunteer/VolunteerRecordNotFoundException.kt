package finda.findavolunteer.application.exception.volunteer

import finda.findavolunteer.global.error.exception.ErrorCode
import finda.findavolunteer.global.error.exception.FindaException

object VolunteerRecordNotFoundException : FindaException(
    ErrorCode.VOLUNTEER_RECORD_NOT_FOUND
)
