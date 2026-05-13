package finda.findavolunteer.global.config

import finda.findavolunteer.global.security.passport.filter.PassportFilter
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(PassportProperties::class)
class SecurityConfig(
    private val passportProperties: PassportProperties
) {

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
                    "/volunteers"
                ).hasAuthority("TEACHER")
                it.requestMatchers(
                    HttpMethod.GET,
                    "/volunteers",
                    "/volunteers/*"
                ).authenticated()
                it.requestMatchers(
                    HttpMethod.DELETE,
                    "/volunteers"
                ).hasAuthority("TEACHER")
                it.requestMatchers(
                    HttpMethod.GET,
                    "/volunteers/*/export"
                ).hasAuthority("TEACHER")
                it.requestMatchers(
                    HttpMethod.POST,
                    "/qr-codes"
                ).hasAuthority("TEACHER")
                it.requestMatchers(
                    HttpMethod.POST,
                    "/qr-codes/attendance"
                ).hasAuthority("STUDENT")
                it.requestMatchers(
                    HttpMethod.POST,
                    "/qr-codes/attendance/students"
                ).hasAuthority("TEACHER")
                it.requestMatchers(
                    HttpMethod.POST,
                    "/activities/user"
                ).hasAuthority("TEACHER")
                it.anyRequest().denyAll()
            }
            .addFilterBefore(passportFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

    @Bean
    fun passportFilter(): PassportFilter {
        return PassportFilter(passportProperties)
    }
}
