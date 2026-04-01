package finda.findavolunteer.domain.volunteer.model

import finda.findavolunteer.domain.activity.model.Activity
import finda.findavolunteer.domain.activity.model.UserActivity
import finda.findavolunteer.domain.participation.model.StudentParticipation
import finda.findavolunteer.domain.volunteer.enum.Weekday

data class VolunteerDetail(
    val volunteer: Volunteer,
    val schedules: List<VolunteerSchedule>,
    val studentParticipations: List<StudentParticipation>,
    val activities: List<Activity>,
    val userActivities: List<UserActivity>,
    val weekdays: List<Weekday>,
    val monthDate: Int?
)
