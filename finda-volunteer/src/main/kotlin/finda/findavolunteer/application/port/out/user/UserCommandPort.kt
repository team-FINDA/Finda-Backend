package finda.findavolunteer.application.port.out.user

import java.util.UUID

interface UserCommandPort {
    fun addVolunteerTime(userId: UUID, volunteerTime: Float)
}
