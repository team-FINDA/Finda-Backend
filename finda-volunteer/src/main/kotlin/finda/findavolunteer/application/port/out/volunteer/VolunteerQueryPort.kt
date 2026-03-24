package finda.findavolunteer.application.port.out.volunteer

import finda.findavolunteer.domain.volunteer.model.Volunteer
import finda.findavolunteer.domain.volunteer.model.VolunteerRecord
import java.util.UUID

interface VolunteerQueryPort {
    fun findByUserId(userId: UUID): List<Volunteer>
    fun findById(id: UUID): Volunteer?
    fun findByIdOrThrow(id: UUID): Volunteer
}