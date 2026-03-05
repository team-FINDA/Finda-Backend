package finda.findanotification.adapter.`in`.kafka.dto

import java.util.UUID

data class VolunteerRemindFiredEvent(
    val volunteerId: UUID
)
