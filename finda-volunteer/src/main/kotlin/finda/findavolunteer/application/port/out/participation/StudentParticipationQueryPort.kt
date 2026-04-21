package finda.findavolunteer.application.port.out.participation

import finda.findavolunteer.domain.participation.model.StudentParticipation
import java.util.UUID

interface StudentParticipationQueryPort {
    fun findById(id: UUID): StudentParticipation?
    fun findByUserIdAndVolunteerId(userId: UUID, volunteerId: UUID): StudentParticipation?
    fun existsByUserIdAndVolunteerId(userId: UUID, volunteerId: UUID): Boolean
}
