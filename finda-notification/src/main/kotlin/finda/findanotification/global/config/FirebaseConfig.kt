package finda.findanotification.global.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import java.io.ByteArrayInputStream

/**
 * 앱 시작 시 서비스 계정 JSON으로 Firebase에 로그인하는 코드
 */
@Configuration
class FirebaseConfig(
    private val environment: Environment
) {

    @PostConstruct
    fun initialize() {
        if (FirebaseApp.getApps().isEmpty()) {
            val serviceAccountJson = environment.getRequiredProperty("firebase.service-account-json")
            ByteArrayInputStream(serviceAccountJson.toByteArray()).use { serviceAccount ->
                val options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build()
                FirebaseApp.initializeApp(options)
            }
        }
    }
}
