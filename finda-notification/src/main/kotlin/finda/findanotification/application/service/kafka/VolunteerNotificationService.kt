package finda.findanotification.application.service.kafka

import finda.findanotification.adapter.`in`.kafka.dto.VolunteerRemindFiredEvent
import finda.findanotification.adapter.`in`.kafka.dto.VolunteerStatusChangedFiredEvent
import finda.findanotification.adapter.out.fcm.FcmClient
import finda.findanotification.adapter.out.grpc.AuthGrpcClient
import finda.findanotification.adapter.out.persistence.notificationpreference.repository.VolunteerNotificationPreferenceRepository
import finda.findanotification.application.port.`in`.kafka.SendVolunteerNotificationUseCase
import finda.findanotification.application.port.out.notification.SaveNotificationPort
import finda.findanotification.domain.notification.enum.NotificationType
import finda.findanotification.domain.notification.model.Notification
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class VolunteerNotificationService(
    private val authGrpcClient: AuthGrpcClient,
    private val fcmClient: FcmClient,
    private val volunteerNotificationPreferenceRepository: VolunteerNotificationPreferenceRepository,
    private val saveNotificationPort: SaveNotificationPort
) : SendVolunteerNotificationUseCase {

    override fun sendStatusChanged(event: VolunteerStatusChangedFiredEvent) {
        val preference = volunteerNotificationPreferenceRepository
            .findByVolunteerId(event.volunteerId.toString())
            ?: return

        if (!preference.enabled) return

        val deviceToken = authGrpcClient.getDeviceToken(preference.userId)
        fcmClient.sendNotification(
            deviceToken = deviceToken,
            title = "봉사 상태가 변경되었습니다",
            body = "상태: ${event.status} / 진행: ${event.progress}"
        )

        saveNotificationPort.save(
            Notification(
                id = UUID.randomUUID(),
                title = "봉사 상태가 변경되었습니다",
                body = "상태: ${event.status} / 진행: ${event.progress}",
                type = NotificationType.NOTIFICATION,
                volunteerId = event.volunteerId.toString()
            )
        )
    }

    override fun sendRemind(event: VolunteerRemindFiredEvent) {
        val preference = volunteerNotificationPreferenceRepository
            .findByVolunteerId(event.volunteerId.toString())
            ?: return

        if (!preference.enabled) return

        val deviceToken = authGrpcClient.getDeviceToken(preference.userId)
        fcmClient.sendNotification(
            deviceToken = deviceToken,
            title = "봉사 활동 리마인드",
            body = "내일 봉사 활동이 있습니다"
        )

        saveNotificationPort.save(
            Notification(
                id = UUID.randomUUID(),
                title = "봉사 활동 리마인드",
                body = "내일 봉사 활동이 있습니다",
                type = NotificationType.NOTIFICATION,
                volunteerId = event.volunteerId.toString()
            )
        )
    }
}
