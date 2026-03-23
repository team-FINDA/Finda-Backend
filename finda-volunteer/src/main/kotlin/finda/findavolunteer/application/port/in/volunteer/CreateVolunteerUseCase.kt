package finda.findavolunteer.application.port.`in`.volunteer

import finda.findavolunteer.adapter.`in`.volunteer.dto.request.CreateVolunteerRequest

interface CreateVolunteerUseCase {
    fun execute(request: CreateVolunteerRequest)
}
