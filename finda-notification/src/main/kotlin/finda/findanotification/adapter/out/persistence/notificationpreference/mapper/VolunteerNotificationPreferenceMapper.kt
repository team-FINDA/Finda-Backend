package finda.findanotification.adapter.out.persistence.notificationpreference.mapper

import finda.findanotification.adapter.out.persistence.GenericMapper
import finda.findanotification.adapter.out.persistence.notificationpreference.entity.VolunteerNotificationPreferenceJpaEntity
import finda.findanotification.domain.notificationpreference.model.VolunteerNotificationPreference
import org.springframework.stereotype.Component

@Component
class VolunteerNotificationPreferenceMapper : GenericMapper<VolunteerNotificationPreference, VolunteerNotificationPreferenceJpaEntity> {

    override fun toDomain(entity: VolunteerNotificationPreferenceJpaEntity?): VolunteerNotificationPreference? {
        return entity?.let {
            VolunteerNotificationPreference(
                id = it.id,
                volunteerId = it.volunteerId,
                userId = it.userId,
                enabled = it.enabled
            )
        }
    }

    override fun toEntity(domain: VolunteerNotificationPreference): VolunteerNotificationPreferenceJpaEntity {
        return VolunteerNotificationPreferenceJpaEntity(
            id = domain.id,
            volunteerId = domain.volunteerId,
            userId = domain.userId,
            enabled = domain.enabled
        )
    }
}
