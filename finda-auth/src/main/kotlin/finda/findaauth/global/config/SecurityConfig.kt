package finda.findaauth.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import finda.findaauth.application.port.out.user.UserQueryPort
import finda.findaauth.global.error.filter.ExceptionFilter
import finda.findaauth.global.security.passport.filter.PassportFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val objectMapper: ObjectMapper,
    private val passportProperties: PassportProperties,
    private val userQueryPort: UserQueryPort
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests {
                it.requestMatchers(
                    "/swagger-ui/**",
                    "/v3/api-docs/**"
                ).permitAll()
                it.requestMatchers(
                    HttpMethod.POST,
                    "/auth/reissue"
                ).permitAll()
                it.requestMatchers(
                    HttpMethod.POST,
                    "/students/send-verification",
                    "/students/verify-email",
                    "/students/signup",
                    "/students/login",
                    "/teachers/verify",
                    "/teachers/send-verification",
                    "/teachers/verify-email",
                    "/teachers/signup",
                    "/teachers/login"
                ).permitAll()
                it.requestMatchers(
                    HttpMethod.GET,
                    "/students"
                ).hasAuthority("TEACHER")
                it.requestMatchers(
                    HttpMethod.GET,
                    "/students/me"
                ).hasAuthority("STUDENT")
                it.requestMatchers(
                    HttpMethod.GET,
                    "/teachers/me"
                ).hasAuthority("TEACHER")
                it.requestMatchers(
                    HttpMethod.POST,
                    "/device-tokens"
                ).authenticated()
                it.anyRequest().denyAll()
            }
            .addFilterBefore(PassportFilter(passportProperties, userQueryPort), UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(ExceptionFilter(objectMapper), PassportFilter::class.java)
            .build()
    }
}
