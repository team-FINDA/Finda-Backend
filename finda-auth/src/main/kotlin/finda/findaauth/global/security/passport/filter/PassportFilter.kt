package finda.findaauth.global.security.passport.filter

import finda.findaauth.application.exception.user.UserNotFoundException
import finda.findaauth.application.port.out.user.UserQueryPort
import finda.findaauth.global.config.PassportProperties
import finda.findaauth.global.security.jwt.exception.InvalidTokenException
import finda.findaauth.global.security.principal.CustomUserDetails
import finda.security.passport.PassportParser
import finda.security.passport.model.Authority
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
    private val passportProperties: PassportProperties,
    private val userQueryPort: UserQueryPort
) : OncePerRequestFilter() {

    private val pathMatcher = AntPathMatcher()

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        // servletPath는 context-path를 제외한 경로를 반환
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
            throw InvalidTokenException
        }

        return try {
            PassportParser.parseAndValidate(passportHeader, passportProperties.secretKey)
        } catch (e: Exception) {
            throw InvalidTokenException
        }
    }

    private fun createAuthentication(passport: Passport): UsernamePasswordAuthenticationToken {
        val authorities = listOf(SimpleGrantedAuthority(passport.authority.name))
        val user = userQueryPort.findById(passport.userId) ?: throw UserNotFoundException

        val details = when (passport.authority) {
            Authority.STUDENT -> CustomUserDetails(
                user = user,
                username = passport.userId.toString(),
                authority = user.authority,
                deletedAt = user.deletedAt
            )
            Authority.TEACHER -> CustomUserDetails(
                user = user,
                username = passport.userId.toString(),
                authority = user.authority,
                deletedAt = user.deletedAt
            )
        }

        return UsernamePasswordAuthenticationToken(details, null, authorities)
    }
}
