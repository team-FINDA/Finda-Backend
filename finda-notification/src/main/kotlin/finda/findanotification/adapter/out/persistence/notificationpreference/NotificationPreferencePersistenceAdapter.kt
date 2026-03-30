package finda.findanotification.adapter.out.persistence.notificationpreference

import finda.findanotification.adapter.out.persistence.notificationpreference.mapper.NotificationPreferenceMapper
import finda.findanotification.adapter.out.persistence.notificationpreference.mapper.VolunteerNotificationPreferenceMapper
import finda.findanotification.adapter.out.persistence.notificationpreference.repository.NotificationPreferenceRepository
import finda.findanotification.adapter.out.persistence.notificationpreference.repository.VolunteerNotificationPreferenceRepository
import finda.findanotification.application.port.out.notificationpreference.NotificationPreferenceCommandPort
import finda.findanotification.application.port.out.notificationpreference.NotificationPreferenceQueryPort
import finda.findanotification.domain.notificationpreference.model.NotificationPreference
import finda.findanotification.domain.notificationpreference.model.VolunteerNotificationPreference
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class NotificationPreferencePersistenceAdapter(
    private val notificationPreferenceRepository: NotificationPreferenceRepository,
    private val notificationPreferenceMapper: NotificationPreferenceMapper,
    private val volunteerNotificationPreferenceRepository: VolunteerNotificationPreferenceRepository,
    private val volunteerNotificationPreferenceMapper: VolunteerNotificationPreferenceMapper
) : NotificationPreferenceQueryPort, NotificationPreferenceCommandPort {

    override fun findAllEnabled(): List<NotificationPreference> {
        return notificationPreferenceRepository.findAllByEnabledTrue()
            .mapNotNull(notificationPreferenceMapper::toDomain)
    }

    override fun findByVolunteerId(volunteerId: UUID): VolunteerNotificationPreference? {
        return volunteerNotificationPreferenceRepository.findByVolunteerId(volunteerId)
            ?.let(volunteerNotificationPreferenceMapper::toDomain)
    }

    override fun save(notificationPreference: NotificationPreference): NotificationPreference {
        val entity = notificationPreferenceRepository.save(notificationPreferenceMapper.toEntity(notificationPreference))
        return requireNotNull(notificationPreferenceMapper.toDomain(entity))
    }

    override fun saveVolunteerPreference(
        volunteerNotificationPreference: VolunteerNotificationPreference
    ): VolunteerNotificationPreference {
        val entity = volunteerNotificationPreferenceRepository.save(
            volunteerNotificationPreferenceMapper.toEntity(volunteerNotificationPreference)
        )
        return requireNotNull(volunteerNotificationPreferenceMapper.toDomain(entity))
    }
}
