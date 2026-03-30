package finda.findanotification.application.port.out.notification

import finda.findanotification.domain.notification.model.Notification

interface NotificationCommandPort {
    fun save(notification: Notification): Notification
}
