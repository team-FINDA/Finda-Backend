package finda.findanotification.application.port.out.notification

import finda.findanotification.domain.notification.model.Notification
import java.util.UUID

interface NotificationQueryPort {
    fun findById(id: UUID): Notification?
}
