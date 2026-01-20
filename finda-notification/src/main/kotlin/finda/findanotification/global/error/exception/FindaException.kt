package finda.findanotification.global.error.exception

import finda.findanotification.global.error.exception.ErrorCode

abstract class FindaException(
    val errorCode: ErrorCode
) : RuntimeException()
