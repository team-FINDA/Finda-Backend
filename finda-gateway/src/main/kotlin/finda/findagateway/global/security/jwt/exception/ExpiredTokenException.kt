package finda.findagateway.global.security.jwt.exception

import finda.findagateway.global.error.exception.ErrorCode
import finda.findagateway.global.error.exception.JwtAuthenticationException

object ExpiredTokenException :
    JwtAuthenticationException(ErrorCode.EXPIRED_TOKEN)
