package finda.findavolunteer.application.port.out.volunteer

import finda.findavolunteer.domain.volunteer.model.Volunteer
import finda.findavolunteer.domain.volunteer.model.recurrence.ActivityRecurrenceMonth
import finda.findavolunteer.domain.volunteer.model.recurrence.ActivityRecurrenceWeek
import java.util.*

interface VolunteerCommandPort {
    fun save(volunteer: Volunteer): Volunteer
    fun saveWeekRecurrence(activityRecurrenceWeek: ActivityRecurrenceWeek): ActivityRecurrenceWeek
    fun saveMonthRecurrence(activityRecurrenceMonth: ActivityRecurrenceMonth): ActivityRecurrenceMonth

    fun deleteById(volunteerId: UUID)
}
