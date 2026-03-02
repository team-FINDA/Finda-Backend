package finda.findanotification.adapter.out.fcm

import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.ApnsConfig
import com.google.firebase.messaging.Aps
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import finda.findanotification.application.port.`in`.devicetoken.DeviceTokenInfo
import finda.findanotification.application.port.`in`.fcm.FcmSendResult
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * FCM으로 푸시 알림을 발송하는 클라이언트
 */
@Component
class FcmClient {
    private val log = LoggerFactory.getLogger(javaClass)

    fun sendNotification(
        deviceTokenInfo: DeviceTokenInfo?,
        title: String,
        body: String
    ): Boolean {
        if (deviceTokenInfo == null || deviceTokenInfo.token.isBlank()) {
            log.debug("FCM skip: null or blank token")
            return false
        }
        return try {
            val message = buildMessage(deviceTokenInfo, title, body)
            FirebaseMessaging.getInstance().send(message)
            true
        } catch (e: Exception) {
            log.warn("FCM send failed: token={}", deviceTokenInfo.token)
            false
        }
    }

    fun sendNotifications(
        deviceTokenInfos: List<DeviceTokenInfo>,
        title: String,
        body: String
    ): FcmSendResult {
        if (deviceTokenInfos.isEmpty()) {
            log.debug("FCM skip: empty token list")
            return FcmSendResult(emptyList(), emptyList())
        }

        val success = mutableListOf<String>()
        val fail = mutableListOf<String>()

        deviceTokenInfos
            .filter { it.token.isNotBlank() }
            .forEach { tokenInfo ->
                if (sendNotification(tokenInfo, title, body)) {
                    success.add(tokenInfo.token)
                } else {
                    fail.add(tokenInfo.token)
                }
            }

        return FcmSendResult(success, fail)
    }

    private fun buildMessage(
        tokenInfo: DeviceTokenInfo,
        title: String,
        body: String
    ): Message {
        val builder = Message.builder()
            .setToken(tokenInfo.token)
            .setNotification(
                Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build()
            )

        when (tokenInfo.os.uppercase()) {
            "IOS" -> builder.setApnsConfig(
                ApnsConfig.builder()
                    .setAps(Aps.builder().setSound("default").build())
                    .build()
            )
            "ANDROID" -> builder.setAndroidConfig(
                AndroidConfig.builder()
                    .setPriority(AndroidConfig.Priority.HIGH)
                    .build()
            )
        }

        return builder.build()
    }
}
