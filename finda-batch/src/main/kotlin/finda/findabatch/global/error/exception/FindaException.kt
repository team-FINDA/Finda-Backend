package finda.findabatch.global.error.exception

import finda.findabatch.global.error.exception.ErrorCode

abstract class FindaException(
    val errorCode: ErrorCode
) : RuntimeException()
