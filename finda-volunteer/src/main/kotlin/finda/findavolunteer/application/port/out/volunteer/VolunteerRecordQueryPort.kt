package finda.findavolunteer.application.port.out.volunteer

import finda.findavolunteer.domain.volunteer.model.VolunteerRecord
import java.util.UUID

interface VolunteerRecordQueryPort {
    fun findById(id: UUID): VolunteerRecord?
    fun findByUserId(userId: UUID): List<VolunteerRecord>
    fun findByVolunteerId(volunteerId: UUID): List<VolunteerRecord>
    fun findByIdOrThrow(id: UUID): VolunteerRecord
}
