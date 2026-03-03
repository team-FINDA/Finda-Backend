package finda.security.passport

import com.fasterxml.jackson.databind.ObjectMapper
import finda.security.passport.exception.InvalidPassportException
import finda.security.passport.exception.InvalidPassportIntegrityException
import finda.security.passport.exception.PassportExpiredException
import finda.security.passport.exception.PassportIssuedInFutureException
import finda.security.passport.model.Passport
import finda.security.passport.util.PassportIntegrityUtil
import java.util.Base64
import javax.crypto.SecretKey

object PassportParser {
    private val objectMapper = ObjectMapper().findAndRegisterModules()

    fun parseAndValidate(passportHeader: String, secretKey: SecretKey): Passport {
        val passport = parse(passportHeader)

        if (!PassportIntegrityUtil.validate(passport, secretKey)) {
            throw InvalidPassportIntegrityException
        }

        val now = System.currentTimeMillis()
        if (now < passport.issuedAt) {
            throw PassportIssuedInFutureException
        }
        if (now > passport.expiresAt) {
            throw PassportExpiredException
        }

        return passport
    }

    fun parse(passportHeader: String): Passport {
        return try {
            val decodedJson = String(Base64.getUrlDecoder().decode(passportHeader))
            objectMapper.readValue(decodedJson, Passport::class.java)
        } catch (e: Exception) {
            throw InvalidPassportException
        }
    }

    fun serialize(passport: Passport): String {
        val json = objectMapper.writeValueAsString(passport)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(json.toByteArray())
    }
}
