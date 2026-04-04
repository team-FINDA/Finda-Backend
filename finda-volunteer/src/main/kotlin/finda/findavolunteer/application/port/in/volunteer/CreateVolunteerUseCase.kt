package finda.findavolunteer.application.port.`in`.volunteer

import finda.findavolunteer.application.port.`in`.volunteer.dto.request.CreateVolunteerCommand

interface CreateVolunteerUseCase {
    fun execute(command: CreateVolunteerCommand)
}
