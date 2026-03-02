package finda.findanotification.adapter.out.fcm

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * FCM으로 푸시 알림을 발송하는 클라이언트
 */
@Component
class FcmClient {

    private val log = LoggerFactory.getLogger(javaClass)

    fun sendNotification(
        deviceToken: String,
        title: String,
        body: String
    ): Boolean {

        if (deviceToken.isBlank()) {
            log.debug("FCM skip: blank token")
            return false
        }

        return try {
            val message = buildMessage(deviceToken, title, body)
            FirebaseMessaging.getInstance().send(message)
            true

        } catch (e: Exception) {
            log.warn("FCM send failed token")
            false
        }
    }

    fun sendNotifications(
        deviceTokens: List<String>,
        title: String,
        body: String
    ): FcmSendResult {

        if (deviceTokens.isEmpty()) {
            log.debug("FCM skip: empty token list")
            return FcmSendResult(emptyList(), emptyList())
        }

        val success = mutableListOf<String>()
        val fail = mutableListOf<String>()

        deviceTokens
            .filter { it.isNotBlank() }
            .forEach { token ->

                if (sendNotification(token, title, body)) {
                    success.add(token)
                } else {
                    fail.add(token)
                }
            }

        return FcmSendResult(success, fail)
    }

    private fun buildMessage(
        token: String,
        title: String,
        body: String
    ): Message =
        Message.builder()
            .setToken(token)
            .setNotification(
                Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build()
            )
            .build()
}

data class FcmSendResult(
    val successTokens: List<String>,
    val failedTokens: List<String>
)
