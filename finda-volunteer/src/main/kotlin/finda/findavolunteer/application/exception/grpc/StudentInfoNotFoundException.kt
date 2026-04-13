package finda.findavolunteer.application.exception.grpc

import finda.findavolunteer.global.error.exception.ErrorCode
import finda.findavolunteer.global.error.exception.FindaException

object StudentInfoNotFoundException : FindaException(ErrorCode.INTERNAL_SERVER_ERROR)
