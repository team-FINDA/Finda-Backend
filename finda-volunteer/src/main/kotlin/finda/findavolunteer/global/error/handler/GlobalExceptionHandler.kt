package finda.findavolunteer.global.error.handler

import finda.findavolunteer.global.error.exception.ErrorCode
import finda.findavolunteer.global.error.exception.FindaException
import finda.findavolunteer.global.error.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(FindaException::class) // FindaException 발생 시 이 메서드 자동 실행
    fun handleWeeShException(e: FindaException): ResponseEntity<ErrorResponse> {
        val errorCode = e.errorCode
        val response = ErrorResponse.of(errorCode, errorCode.message())
        return ResponseEntity(response, HttpStatus.valueOf(errorCode.status()))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class) // Valid 예외 발생 시 실행
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.BAD_REQUEST
        val response = ErrorResponse.of(errorCode, errorCode.message())
        return ResponseEntity(response, HttpStatus.valueOf(errorCode.status()))
    }

    @ExceptionHandler(Exception::class) // 예상치 못한 에러
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.INTERNAL_SERVER_ERROR
        val response = ErrorResponse.of(errorCode, e.message ?: "Unknown error")
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
