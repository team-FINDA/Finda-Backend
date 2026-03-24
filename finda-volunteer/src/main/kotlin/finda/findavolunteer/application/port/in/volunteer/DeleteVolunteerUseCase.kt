package finda.findavolunteer.application.port.`in`.volunteer

import java.util.*

interface DeleteVolunteerUseCase {
    fun execute(volunteerId: UUID)
}