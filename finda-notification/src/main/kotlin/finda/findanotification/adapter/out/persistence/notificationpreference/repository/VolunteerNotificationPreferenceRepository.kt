package finda.findanotification.adapter.out.persistence.notificationpreference.repository

import finda.findanotification.adapter.out.persistence.notificationpreference.entity.VolunteerNotificationPreferenceJpaEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface VolunteerNotificationPreferenceRepository : CrudRepository<VolunteerNotificationPreferenceJpaEntity, String> {
    fun findByVolunteerId(volunteerId: UUID): VolunteerNotificationPreferenceJpaEntity?
}
