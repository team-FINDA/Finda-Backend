package finda.findagateway.global.error.exception

import org.springframework.security.core.AuthenticationException

/**
 * JWT 인증 과정에서 발생하는 예외의 공통 부모 클래스임
 * @RestControllerAdvice에서 처리되지 않기 때문에
 * Spring Security 인증 흐름으로 전달하기 위해 AuthenticationException을 상속함
 * open으로 선언하여 세부 예외 확장 가능하게 함
 */
open class JwtAuthenticationException(
    val errorCode: ErrorCode
) : AuthenticationException(errorCode.message())
