package finda.findagateway.global.security.jwt.exception

import finda.findagateway.global.error.ErrorCode
import finda.findagateway.global.error.FindaException

object UnexpectedTokenException :
    FindaException(ErrorCode.UNEXPECTED_TOKEN)
