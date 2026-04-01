package finda.findavolunteer.application.port.`in`.volunteer

import finda.findavolunteer.adapter.`in`.volunteer.dto.response.VolunteerDetailResponse
import java.util.UUID

interface VolunteerDetailUseCase {
    fun execute(volunteerId: UUID): VolunteerDetailResponse
}
