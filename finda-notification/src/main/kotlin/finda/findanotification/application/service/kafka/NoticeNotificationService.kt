package finda.findanotification.application.service.kafka

import finda.findanotification.adapter.out.fcm.FcmClient
import finda.findanotification.adapter.out.grpc.AuthGrpcClient
import finda.findanotification.adapter.out.persistence.notice.repository.NoticeRepository
import finda.findanotification.adapter.out.persistence.notificationpreference.repository.NotificationPreferenceRepository
import finda.findanotification.application.port.`in`.kafka.SendNoticeNotificationUseCase
import finda.findanotification.application.port.`in`.kafka.dto.NoticeScheduledEvent
import finda.findanotification.application.port.out.notification.SaveNotificationPort
import finda.findanotification.domain.notice.model.Notice
import finda.findanotification.domain.notification.enum.NotificationType
import finda.findanotification.domain.notification.model.Notification
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class NoticeNotificationService(
    private val authGrpcClient: AuthGrpcClient,
    private val fcmClient: FcmClient,
    private val notificationPreferenceRepository: NotificationPreferenceRepository,
    private val saveNotificationPort: SaveNotificationPort,
    private val noticeRepository: NoticeRepository
) : SendNoticeNotificationUseCase {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun send(event: NoticeScheduledEvent) {
        val notice = noticeRepository.findByIdOrNull(event.noticeId) ?: return
        sendToAllUsers(notice.title, notice.body)
    }

    private fun sendToAllUsers(title: String, body: String) {
        val userIds = notificationPreferenceRepository.findAllByEnabledTrue()
            .map { it.userId }

        if (userIds.isEmpty()) {
            return
        }

        try {
            val deviceTokens = authGrpcClient.getDeviceTokens(userIds)
            if (deviceTokens.isEmpty()) {
                return
            }

            fcmClient.sendNotifications(deviceTokens, title, body)

            saveNotificationPort.save(
                Notification(
                    id = UUID.randomUUID(),
                    title = title,
                    body = body,
                    type = NotificationType.NOTIFICATION,
                    volunteerId = null
                )
            )
        } catch (e: Exception) {
            /**
             * 추후 재시도 로직 추가
             */
            log.error("Notice Notification 전송 중 오류 발생: ${e.message}", e)
        }
    }
}
