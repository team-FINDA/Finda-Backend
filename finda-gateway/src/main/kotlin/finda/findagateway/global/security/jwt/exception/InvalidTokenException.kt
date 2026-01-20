package finda.findagateway.global.security.jwt.exception

import finda.findagateway.global.error.exception.ErrorCode
import finda.findagateway.global.error.exception.JwtAuthenticationException

object InvalidTokenException :
    JwtAuthenticationException(ErrorCode.INVALID_TOKEN)
