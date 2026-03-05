package finda.security.passport.exception

import finda.error.ErrorCode
import finda.error.FindaException

object InvalidPassportIntegrityException : FindaException(ErrorCode.INVALID_PASSPORT_INTEGRITY)
