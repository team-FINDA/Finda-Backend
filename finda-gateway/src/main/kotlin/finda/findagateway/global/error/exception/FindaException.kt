package finda.findagateway.global.error.exception

abstract class FindaException(
    val errorCode: ErrorCode
) : RuntimeException()
