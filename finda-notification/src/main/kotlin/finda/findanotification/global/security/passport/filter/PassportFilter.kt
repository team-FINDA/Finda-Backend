package finda.findanotification.global.security.passport.filter

import finda.findanotification.global.config.PassportProperties
import finda.security.passport.PassportParser
import finda.security.passport.exception.InvalidPassportException
import finda.security.passport.model.Passport
import finda.security.passport.propertice.PassportSecurityProperties
import finda.security.path.SecurityPath
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.AntPathMatcher
import org.springframework.web.filter.OncePerRequestFilter

class PassportFilter(
    private val passportProperties: PassportProperties
) : OncePerRequestFilter() {

    private val pathMatcher = AntPathMatcher()

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.servletPath
        val shouldSkip = SecurityPath.PERMIT_ALL_PATHS.any { permitPath ->
            pathMatcher.match(permitPath, path)
        }
        return shouldSkip
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val passportHeader = request.getHeader(PassportSecurityProperties.PASSPORT_HEADER)
        val passport = resolvePassport(passportHeader)

        SecurityContextHolder.clearContext()

        passport.let {
            val authentication = createAuthentication(it)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }

    private fun resolvePassport(passportHeader: String?): Passport {
        if (passportHeader.isNullOrBlank()) {
            throw InvalidPassportException
        }

        return try {
            PassportParser.parseAndValidate(passportHeader, passportProperties.secretKey)
        } catch (e: Exception) {
            throw InvalidPassportException
        }
    }

    private fun createAuthentication(passport: Passport): UsernamePasswordAuthenticationToken {
        val authorities = listOf(SimpleGrantedAuthority(passport.authority.name))

        // Passport 정보를 Principal로 사용
        return UsernamePasswordAuthenticationToken(passport, null, authorities)
    }
}
