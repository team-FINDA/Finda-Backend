package finda.findaauth.application.port.`in`.student

import java.util.UUID

interface AddVolunteerTimeUseCase {
    fun execute(userId: UUID, volunteerTime: Float)
}
