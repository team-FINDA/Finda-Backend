package finda.findanotification.adapter.out.fcm

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import org.springframework.stereotype.Component

/**
 * FCM으로 푸시 알림을 발송하는 클라이언트
 */
@Component
class FcmClient {

    fun sendNotification(deviceToken: String, title: String, body: String) {
        val message = Message.builder()
            .setToken(deviceToken)
            .setNotification(
                Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build()
            )
            .build()

        FirebaseMessaging.getInstance().send(message)
    }

    fun sendNotifications(deviceTokens: List<String>, title: String, body: String) {
        deviceTokens.forEach { token ->
            sendNotification(token, title, body)
        }
    }
}
