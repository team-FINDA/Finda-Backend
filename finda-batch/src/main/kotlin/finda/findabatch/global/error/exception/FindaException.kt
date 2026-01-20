package finda.findabatch.global.error.exception

abstract class FindaException(
    val errorCode: ErrorCode
) : RuntimeException()
