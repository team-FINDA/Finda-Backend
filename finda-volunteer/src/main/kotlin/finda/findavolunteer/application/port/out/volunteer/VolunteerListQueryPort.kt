package finda.findavolunteer.application.port.out.volunteer

import finda.findavolunteer.domain.volunteer.enum.VolunteerSortBy
import finda.findavolunteer.domain.volunteer.enum.VolunteerStatus
import finda.findavolunteer.domain.volunteer.model.Volunteer

interface VolunteerListQueryPort {
    fun findAll(
        status: VolunteerStatus?,
        year: Int?,
        sortBy: VolunteerSortBy
    ): List<Volunteer>
}
