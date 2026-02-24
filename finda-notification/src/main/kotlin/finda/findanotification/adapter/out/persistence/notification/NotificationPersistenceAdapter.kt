package finda.findanotification.adapter.out.persistence.notification

import finda.findanotification.adapter.out.persistence.notification.mapper.NotificationMapper
import finda.findanotification.adapter.out.persistence.notification.repository.NotificationRepository
import finda.findanotification.application.port.out.SaveNotificationPort
import finda.findanotification.domain.notification.model.Notification
import org.springframework.stereotype.Component

@Component
class NotificationPersistenceAdapter(
    private val notificationRepository: NotificationRepository,
    private val notificationMapper: NotificationMapper
) : SaveNotificationPort {

    override fun save(notification: Notification): Notification {
        val entity = notificationMapper.toEntity(notification)
        val saved = notificationRepository.save(entity)
        return notificationMapper.toDomain(saved)!!
    }
}
