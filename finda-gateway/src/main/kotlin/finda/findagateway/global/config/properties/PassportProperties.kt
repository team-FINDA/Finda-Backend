package finda.findagateway.global.config.properties

import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.ConfigurationProperties
import javax.crypto.SecretKey

@ConfigurationProperties(prefix = "passport")
class PassportProperties(
    secretKey: String
) {
    private val secretKeyValue: String = secretKey
    val key: SecretKey
        get() = Keys.hmacShaKeyFor(secretKeyValue.toByteArray(Charsets.UTF_8))
}
