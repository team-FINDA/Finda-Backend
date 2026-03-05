package finda.findanotification.application.port.`in`.kafka

import finda.findanotification.adapter.`in`.kafka.dto.VolunteerRemindFiredEvent
import finda.findanotification.adapter.`in`.kafka.dto.VolunteerStatusChangedFiredEvent

interface SendVolunteerNotificationUseCase {
    fun sendStatusChanged(event: VolunteerStatusChangedFiredEvent)
    fun sendRemind(event: VolunteerRemindFiredEvent)
}
