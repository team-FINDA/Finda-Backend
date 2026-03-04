package finda.findagateway.global.error

abstract class FindaException(
    val errorCode: ErrorCode
) : RuntimeException()
