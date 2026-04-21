package finda.findavolunteer.application.port.out.participation

import finda.findavolunteer.domain.participation.model.TeacherParticipation
import java.util.*

interface TeacherParticipationQueryPort {
    fun findByUserId(userId: UUID): TeacherParticipation?
    fun findByUserIdOrThrow(userId: UUID): TeacherParticipation
    fun findByTeacherIdAndVolunteerId(userId: UUID, volunteerId: UUID): TeacherParticipation?
    fun findByTeacherIdAndVolunteerIdOrThrow(userId: UUID, volunteerId: UUID): TeacherParticipation
    fun existsByTeacherIdAndVolunteerId(userId: UUID, volunteerId: UUID): Boolean
}
