package finda.findavolunteer.application.exception.participation

import finda.findavolunteer.global.error.exception.ErrorCode
import finda.findavolunteer.global.error.exception.FindaException

object UserParticipationForbiddenException: FindaException (
    ErrorCode.STUDENT_PARTICIPATION_FORBIDDEN
)