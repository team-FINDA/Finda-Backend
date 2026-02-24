package finda.findanotification.application.port.out

import finda.findanotification.domain.notification.model.Notification

interface SaveNotificationPort {
    fun save(notification: Notification): Notification
}
