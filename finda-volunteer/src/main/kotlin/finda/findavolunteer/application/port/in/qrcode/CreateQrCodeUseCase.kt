package finda.findavolunteer.application.port.`in`.qrcode

import finda.findavolunteer.application.port.`in`.qrcode.dto.request.CreateQrCodeCommand

interface CreateQrCodeUseCase {
    fun execute(request: CreateQrCodeCommand): String
}
