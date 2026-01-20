package finda.findagateway.global.error.handler

import com.fasterxml.jackson.databind.ObjectMapper
import finda.findagateway.global.error.exception.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class CustomAccessDeniedHandler(
    private val objectMapper: ObjectMapper
) : ServerAccessDeniedHandler {

    override fun handle(
        exchange: ServerWebExchange,
        denied: AccessDeniedException
    ): Mono<Void> {
        val errorCode = ErrorCode.FORBIDDEN
        val response = exchange.response

        response.statusCode = HttpStatus.valueOf(errorCode.status())
        response.headers.contentType = MediaType.APPLICATION_JSON

        val body = mapOf(
            "status" to errorCode.status(),
            "message" to errorCode.message(),
            "code" to errorCode.code()
        )

        val buffer = response.bufferFactory()
            .wrap(objectMapper.writeValueAsBytes(body))

        return response.writeWith(Mono.just(buffer))
    }
}
