package finda.findanotification.application.port.out.notification

import finda.findanotification.domain.notification.model.Notification

interface SaveNotificationPort {
    fun save(notification: Notification): Notification
}
