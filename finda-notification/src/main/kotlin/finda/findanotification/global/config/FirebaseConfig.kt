package finda.findanotification.global.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

/**
 * 앱 시작 시 서비스 계정 JSON으로 Firebase에 로그인하는 코드
 */
@Configuration
class FirebaseConfig {

    @PostConstruct
    fun initialize() {
        val serviceAccount = ClassPathResource("firebase-service-account.json").inputStream
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()
        FirebaseApp.initializeApp(options)
    }
}
