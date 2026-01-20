package finda.findanotification.global.error.exception

abstract class FindaException(
    val errorCode: ErrorCode
) : RuntimeException()
