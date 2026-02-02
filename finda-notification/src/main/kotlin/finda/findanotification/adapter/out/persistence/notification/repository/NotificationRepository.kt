package finda.findanotification.adapter.out.persistence.notification.repository

import finda.findanotification.adapter.out.persistence.notification.entity.NotificationJpaEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface NotificationRepository : CrudRepository<NotificationJpaEntity, UUID>
