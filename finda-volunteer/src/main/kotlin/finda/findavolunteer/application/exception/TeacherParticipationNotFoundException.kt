package finda.findavolunteer.application.exception

import finda.findavolunteer.global.error.exception.ErrorCode
import finda.findavolunteer.global.error.exception.FindaException

object TeacherParticipationNotFoundException : FindaException(
    ErrorCode.TEACHER_PARTICIPATION_NOT_FOUND
)
