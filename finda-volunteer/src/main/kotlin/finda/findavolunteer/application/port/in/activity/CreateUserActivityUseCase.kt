package finda.findavolunteer.application.port.`in`.activity

import finda.findavolunteer.application.port.`in`.activity.dto.request.CreateUserActivityCommand

interface CreateUserActivityUseCase {
    fun execute(command: CreateUserActivityCommand)
}
