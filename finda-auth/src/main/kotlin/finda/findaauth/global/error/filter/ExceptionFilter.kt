package finda.findaauth.global.error.filter

import com.fasterxml.jackson.databind.ObjectMapper
import finda.findaauth.global.error.exception.ErrorCode
import finda.findaauth.global.error.exception.FindaException
import finda.findaauth.global.error.response.ErrorResponse
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter
import java.nio.charset.StandardCharsets

class ExceptionFilter(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: FindaException) {
            errorToJson(e.errorCode, response)
        } catch (e: Exception) {
            when (e.cause) {
                is FindaException -> {
                    errorToJson((e.cause as FindaException).errorCode, response)
                }
                else -> {
                    errorToJson(ErrorCode.INTERNAL_SERVER_ERROR, response)
                }
            }
        }
    }

    private fun errorToJson(errorCode: ErrorCode, response: HttpServletResponse) {
        response.status = errorCode.status()
        response.characterEncoding = StandardCharsets.UTF_8.name()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(objectMapper.writeValueAsString(ErrorResponse.of(errorCode, errorCode.message())))
    }
}
