package finda.findavolunteer.global.error.exception

import finda.findavolunteer.global.error.exception.ErrorCode

abstract class FindaException(
    val errorCode: ErrorCode
) : RuntimeException()
