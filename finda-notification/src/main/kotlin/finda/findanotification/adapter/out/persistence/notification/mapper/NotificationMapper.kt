package finda.findanotification.adapter.out.persistence.notification.mapper

import finda.findanotification.adapter.out.persistence.GenericMapper
import finda.findanotification.adapter.out.persistence.notification.entity.NotificationJpaEntity
import finda.findanotification.domain.notification.model.Notification
import org.springframework.stereotype.Component

@Component
class NotificationMapper : GenericMapper<Notification, NotificationJpaEntity> {

    override fun toDomain(entity: NotificationJpaEntity?): Notification? {
        return entity?.let {
            Notification(
                id = it.id!!,
                title = it.title,
                body = it.body,
                type = it.type,
                volunteerId = it.volunteerId
            )
        }
    }

    override fun toEntity(domain: Notification): NotificationJpaEntity {
        return NotificationJpaEntity(
            id = domain.id,
            title = domain.title,
            body = domain.body,
            type = domain.type,
            volunteerId = domain.volunteerId
        )
    }
}
