package finda.findanotification.adapter.out.persistence.notification

import finda.findanotification.adapter.out.persistence.notification.mapper.NotificationMapper
import finda.findanotification.adapter.out.persistence.notification.repository.NotificationRepository
import finda.findanotification.application.port.out.notification.NotificationCommandPort
import finda.findanotification.application.port.out.notification.NotificationQueryPort
import finda.findanotification.application.port.out.notification.SaveNotificationPort
import finda.findanotification.domain.notification.model.Notification
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class NotificationPersistenceAdapter(
    private val notificationRepository: NotificationRepository,
    private val notificationMapper: NotificationMapper
) : SaveNotificationPort, NotificationCommandPort, NotificationQueryPort {

    override fun save(notification: Notification): Notification {
        val entity = notificationMapper.toEntity(notification)
        val saved = notificationRepository.save(entity)
        return notificationMapper.toDomain(saved)!!
    }

    override fun findById(id: UUID): Notification? {
        return notificationRepository.findByIdOrNull(id)
            ?.let(notificationMapper::toDomain)
    }
}
