package finda.security.passport.exception

import finda.error.ErrorCode
import finda.error.FindaException

object InvalidPassportException : FindaException(ErrorCode.INVALID_PASSPORT)
