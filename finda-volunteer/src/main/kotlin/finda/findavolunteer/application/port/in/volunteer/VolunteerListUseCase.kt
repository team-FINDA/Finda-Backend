package finda.findavolunteer.application.port.`in`.volunteer

import finda.findavolunteer.adapter.`in`.volunteer.dto.response.VolunteerListResponse
import finda.findavolunteer.domain.volunteer.enum.VolunteerStatus

interface VolunteerListUseCase {
    fun execute(
        status: VolunteerStatus?,
        year: Int?,
        sortBy: String?
    ): List<VolunteerListResponse>
}
