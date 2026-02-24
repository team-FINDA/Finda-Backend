package finda.findanotification.application.service.kafka

import finda.findanotification.adapter.`in`.kafka.dto.NoticeScheduledFiredEvent
import finda.findanotification.adapter.out.fcm.FcmClient
import finda.findanotification.adapter.out.grpc.AuthGrpcClient
import finda.findanotification.adapter.out.persistence.notificationpreference.repository.NotificationPreferenceRepository
import finda.findanotification.application.port.`in`.kafka.SendNoticeNotificationUseCase
import finda.findanotification.application.port.out.SaveNotificationPort
import finda.findanotification.domain.notification.enum.NotificationType
import finda.findanotification.domain.notification.model.Notification
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class NoticeNotificationService(
    private val authGrpcClient: AuthGrpcClient,
    private val fcmClient: FcmClient,
    private val notificationPreferenceRepository: NotificationPreferenceRepository,
    private val saveNotificationPort: SaveNotificationPort
) : SendNoticeNotificationUseCase {

    override fun send(event: NoticeScheduledFiredEvent) {
        val userIds = notificationPreferenceRepository.findAllByEnabledTrue()
            .map { it.userId }

        val deviceTokens = authGrpcClient.getDeviceTokens(userIds)

        fcmClient.sendNotifications(
            deviceTokens = deviceTokens,
            title = "새 공지사항이 등록되었습니다",
            body = "공지사항을 확인해주세요"
        )

        saveNotificationPort.save(
            Notification(
                id = UUID.randomUUID(),
                title = "새 공지사항이 등록되었습니다",
                body = "공지사항을 확인해주세요",
                type = NotificationType.NOTIFICATION,
                volunteerId = null
            )
        )
    }
}
