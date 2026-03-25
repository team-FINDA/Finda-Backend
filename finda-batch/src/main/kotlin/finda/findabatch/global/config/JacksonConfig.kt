package finda.findabatch.global.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * ObjectMapper의 중복 생성 방지와
 * 애플리케이션 전역에서 일관된 JSON 직렬화/역직렬화 설정 적용을 위한 클래스
 */
@Configuration
class JacksonConfig {

    @Bean
    fun objectMapper(): ObjectMapper {
        val javaTimeModule = JavaTimeModule().apply {
            addDeserializer(
                LocalTime::class.java,
                object : LocalTimeDeserializer(DateTimeFormatter.ISO_LOCAL_TIME) {
                    override fun deserialize(
                        parser: com.fasterxml.jackson.core.JsonParser,
                        ctxt: com.fasterxml.jackson.databind.DeserializationContext
                    ): LocalTime {
                        return when (parser.currentToken()) {
                            com.fasterxml.jackson.core.JsonToken.VALUE_NUMBER_INT -> {
                                val node = parser.longValue
                                LocalTime.ofSecondOfDay(node / 1000 % 86400)
                            }
                            com.fasterxml.jackson.core.JsonToken.VALUE_STRING -> {
                                super.deserialize(parser, ctxt)
                            }
                            else -> throw IllegalArgumentException("Cannot parse LocalTime from ${parser.currentToken()}")
                        }
                    }
                }
            )
        }

        return ObjectMapper().apply {
            registerKotlinModule()
            registerModule(javaTimeModule)
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        }
    }
}
