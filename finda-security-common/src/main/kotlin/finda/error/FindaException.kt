package finda.error

abstract class FindaException(
    val errorCode: ErrorCode
) : RuntimeException()
