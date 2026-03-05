package finda.findanotification.global.config

import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.ConfigurationProperties
import javax.crypto.SecretKey

@ConfigurationProperties(prefix = "passport")
class PassportProperties(
    secretKey: String
) {
    val secretKey: SecretKey = Keys.hmacShaKeyFor(
        secretKey.toByteArray(Charsets.UTF_8)
    )
}
