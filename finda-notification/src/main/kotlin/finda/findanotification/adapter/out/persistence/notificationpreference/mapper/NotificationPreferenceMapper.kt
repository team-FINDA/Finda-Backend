package finda.findanotification.adapter.out.persistence.notificationpreference.mapper

import finda.findanotification.adapter.out.persistence.GenericMapper
import finda.findanotification.adapter.out.persistence.notificationpreference.entity.NotificationPreferenceJpaEntity
import finda.findanotification.domain.notificationpreference.model.NotificationPreference
import org.springframework.stereotype.Component

@Component
class NotificationPreferenceMapper : GenericMapper<NotificationPreference, NotificationPreferenceJpaEntity> {

    override fun toDomain(entity: NotificationPreferenceJpaEntity?): NotificationPreference? {
        return entity?.let {
            NotificationPreference(
                id = it.id!!,
                type = it.type,
                userId = it.userId,
                enabled = it.enabled
            )
        }
    }

    override fun toEntity(domain: NotificationPreference): NotificationPreferenceJpaEntity {
        return NotificationPreferenceJpaEntity(
            id = domain.id,
            type = domain.type,
            userId = domain.userId,
            enabled = domain.enabled
        )
    }
}
