package finda.security.passport.exception

import finda.error.ErrorCode
import finda.error.FindaException

object PassportExpiredException : FindaException(ErrorCode.PASSPORT_EXPIRED)
