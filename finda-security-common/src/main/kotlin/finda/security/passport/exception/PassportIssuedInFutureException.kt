package finda.security.passport.exception

import finda.error.ErrorCode
import finda.error.FindaException

object PassportIssuedInFutureException : FindaException(ErrorCode.PASSPORT_ISSUED_IN_FUTURE)
