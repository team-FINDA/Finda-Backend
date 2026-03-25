package finda.findavolunteer.application.port.out.volunteer

import finda.findavolunteer.domain.volunteer.model.VolunteerSchedule

interface VolunteerScheduleCommandPort {
    fun save(volunteerSchedule: VolunteerSchedule): VolunteerSchedule
}
