package finda.security.passport

import finda.security.passport.model.Passport
import finda.security.passport.util.PassportIntegrityUtil
import javax.crypto.SecretKey

object PassportVerifier {
    fun validate(passport: Passport, secretKey: SecretKey): Boolean {
        return PassportIntegrityUtil.validate(passport, secretKey)
    }
}
