package finda.findabatch.infra.event.dto.volunteer

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class CdcEvent<T>(
    val before: T?,
    val after: T?,
    val op: String
)

data class VolunteerSnapshot(
    val id: UUID,
    @JsonProperty("remind_time") val remindTime: String?,
    @JsonProperty("user_id") val userId: String?,
    val status: String?
)

data class VolunteerScheduleSnapshot(
    val id: UUID,
    @JsonProperty("volunteer_id") val volunteerId: UUID,
    val date: String
)

data class RecurrenceWeekSnapshot(
    val id: UUID,
    @JsonProperty("volunteer_id") val volunteerId: UUID,
    val weekday: String
)

data class RecurrenceMonthSnapshot(
    val id: UUID,
    @JsonProperty("volunteer_id") val volunteerId: UUID,
    val day: Int
)
