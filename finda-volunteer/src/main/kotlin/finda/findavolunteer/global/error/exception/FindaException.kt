package finda.findavolunteer.global.error.exception

abstract class FindaException(
    val errorCode: ErrorCode
) : RuntimeException()
