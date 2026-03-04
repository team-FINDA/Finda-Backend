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
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class VolunteerNotificationService(
    private val authGrpcClient: AuthGrpcClient,
    private val fcmClient: FcmClient,
    private val volunteerNotificationPreferenceRepository: VolunteerNotificationPreferenceRepository,
    private val saveNotificationPort: SaveNotificationPort
) : SendVolunteerNotificationUseCase {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun sendStatusChanged(event: VolunteerStatusChangedFiredEvent) {
        val preference = volunteerNotificationPreferenceRepository
            .findByVolunteerId(event.volunteerId)
            ?: return

        if (!preference.enabled) return

        try {
            val deviceToken = authGrpcClient.getDeviceToken(preference.userId)
            val title = "봉사 상태가 변경되었습니다"
            val body = "상태: ${event.status} / 진행: ${event.progress}"

            fcmClient.sendNotification(deviceToken, title, body)

            saveNotificationPort.save(
                Notification(
                    id = UUID.randomUUID(),
                    title = title,
                    body = body,
                    type = NotificationType.NOTIFICATION,
                    volunteerId = event.volunteerId.toString()
                )
            )
        } catch (e: Exception) {
            log.error("Volunteer Status Changed Notification 전송 중 오류 발생: ${e.message}", e)
        }
    }

    override fun sendRemind(event: VolunteerRemindFiredEvent) {
        val preference = volunteerNotificationPreferenceRepository
            .findByVolunteerId(event.volunteerId)
            ?: return

        if (!preference.enabled) return

        try {
            val deviceToken = authGrpcClient.getDeviceToken(preference.userId)
            val title = "봉사 활동 리마인드"
            val body = "오늘 봉사 활동이 있습니다"

            fcmClient.sendNotification(deviceToken, title, body)

            saveNotificationPort.save(
                Notification(
                    id = UUID.randomUUID(),
                    title = title,
                    body = body,
                    type = NotificationType.NOTIFICATION,
                    volunteerId = event.volunteerId.toString()
                )
            )
        } catch (e: Exception) {
            log.error("Volunteer Remind Notification 전송 중 오류 발생: ${e.message}", e)
        }
    }
}
