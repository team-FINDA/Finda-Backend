package finda.findavolunteer.application.port.`in`.volunteer

import finda.findavolunteer.application.port.`in`.volunteer.dto.response.VolunteerListResult
import finda.findavolunteer.domain.volunteer.enum.VolunteerStatus

interface VolunteerListUseCase {
    fun execute(
        status: VolunteerStatus?,
        year: Int?,
        sortBy: String?
    ): List<VolunteerListResult>
}
