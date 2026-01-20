package finda.findagateway.global.error.handler

import com.fasterxml.jackson.databind.ObjectMapper
import finda.findagateway.global.error.exception.ErrorCode
import finda.findagateway.global.error.exception.JwtAuthenticationException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class CustomAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper
) : ServerAuthenticationEntryPoint {

    override fun commence(
        exchange: ServerWebExchange,
        ex: AuthenticationException
    ): Mono<Void> {
        val errorCode =
            (ex as? JwtAuthenticationException)?.errorCode
                ?: ErrorCode.UNEXPECTED_TOKEN

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
