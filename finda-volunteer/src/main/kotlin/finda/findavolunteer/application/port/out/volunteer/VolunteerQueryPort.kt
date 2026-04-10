package finda.findavolunteer.application.port.out.volunteer

import finda.findavolunteer.domain.volunteer.model.Volunteer
import java.util.UUID

interface VolunteerQueryPort {
    fun findAllByUserId(userId: UUID): List<Volunteer>
    fun findById(id: UUID): Volunteer?
    fun findByIdOrThrow(id: UUID): Volunteer
    fun findAllWithRemindTime(): List<Volunteer>
    fun findTopActivitiesByUserId(userId: UUID): List<String>
}
