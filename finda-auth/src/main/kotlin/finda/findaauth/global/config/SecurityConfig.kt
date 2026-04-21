package finda.findaauth.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import finda.findaauth.application.port.out.user.UserQueryPort
import finda.findaauth.global.error.filter.ExceptionFilter
import finda.findaauth.global.security.passport.filter.PassportFilter
import finda.security.path.SecurityPath
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
                    "/auth/test",
                    "/auth/reissue",
                    "/students/signup",
                    "/students/login",
                    "/teachers/signup",
                    "/teachers/login",
                    "/teachers/verify",
                    "/teachers/verify-email",
                    "/students/send-verification",
                    "/teachers/send-verification",
                    "/students/verify-email",
                    "/email/**"
                ).permitAll()
                it.requestMatchers(HttpMethod.GET, "/students").hasAuthority("TEACHER")
                    .anyRequest().authenticated()
            }
            .addFilterBefore(PassportFilter(passportProperties, userQueryPort), UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(ExceptionFilter(objectMapper), PassportFilter::class.java)
            .build()
    }
}
