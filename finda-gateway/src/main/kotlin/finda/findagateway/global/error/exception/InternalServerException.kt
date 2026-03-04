package finda.findagateway.global.error.exception

import finda.findagateway.global.error.ErrorCode
import finda.findagateway.global.error.FindaException

object InternalServerException:FindaException(
    ErrorCode.INTERNAL_SERVER_ERROR,
)