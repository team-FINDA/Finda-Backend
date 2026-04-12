package finda.findavolunteer.application.port.out.volunteer

import finda.findavolunteer.domain.volunteer.model.VolunteerDetail
import java.util.UUID

interface VolunteerDetailQueryPort {
    fun findDetailByIdOrThrow(volunteerId: UUID): VolunteerDetail
}
