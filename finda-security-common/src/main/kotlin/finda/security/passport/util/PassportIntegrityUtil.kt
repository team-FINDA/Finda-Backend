package finda.security.passport.util

import finda.security.passport.model.Authority
import finda.security.passport.model.Passport
import finda.security.passport.propertice.PassportSecurityProperties.HMAC_ALGORITHM
import java.security.MessageDigest
import java.util.Base64
import java.util.UUID
import javax.crypto.Mac
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

object PassportIntegrityUtil {
    fun generate(
        userId: UUID,
        authority: Authority,
        secretKey: SecretKey
    ): String {
        val data = buildDataString(userId, authority)
        return computeHMAC(data, secretKey)
    }

    fun validate(passport: Passport, secretKey: SecretKey): Boolean {
        val expected = generate(passport.userId, passport.authority, secretKey)

        return MessageDigest.isEqual(
            expected.toByteArray(),
            passport.userIntegrity.toByteArray()
        )
    }

    private fun buildDataString(userId: UUID, authority: Authority): String {
        return "$userId|${authority.name}"
    }

    private fun computeHMAC(data: String, secretKey: SecretKey): String {
        val hmacKey = SecretKeySpec(secretKey.encoded, HMAC_ALGORITHM)
        val mac = Mac.getInstance(HMAC_ALGORITHM)
        mac.init(hmacKey)
        val hmacBytes = mac.doFinal(data.toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(hmacBytes)
    }
}
