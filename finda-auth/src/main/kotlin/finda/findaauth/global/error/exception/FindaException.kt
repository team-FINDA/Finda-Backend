package finda.findaauth.global.error.exception

abstract class FindaException(
    val errorCode: ErrorCode
) : RuntimeException()
