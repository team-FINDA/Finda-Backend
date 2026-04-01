package finda.findavolunteer.application.port.`in`.activity

import finda.findavolunteer.adapter.`in`.activity.dto.request.CreateUserActivityRequest

interface CreateUserActivityUseCase {
    fun execute(request: CreateUserActivityRequest)
}
