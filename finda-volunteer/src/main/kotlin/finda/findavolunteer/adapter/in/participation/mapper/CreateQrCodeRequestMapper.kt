package finda.findavolunteer.adapter.`in`.participation.mapper

import finda.findavolunteer.adapter.`in`.participation.dto.request.CreateQrCodeRequest
import finda.findavolunteer.application.port.`in`.qrcode.dto.request.CreateQrCodeCommand

fun CreateQrCodeRequest.toCommand(): CreateQrCodeCommand =
    CreateQrCodeCommand(
        volunteerId = volunteerId
    )
