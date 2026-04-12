package finda.findavolunteer.application.port.`in`.volunteer

import finda.findavolunteer.application.port.`in`.volunteer.dto.response.VolunteerDetailResult
import java.util.UUID

interface VolunteerDetailUseCase {
    fun execute(volunteerId: UUID): VolunteerDetailResult
}
