package finda.findavolunteer.application.port.`in`.qrcode

import finda.findavolunteer.application.port.`in`.qrcode.dto.request.AttendanceQrCodeCommand

interface AttendanceQrCodeUseCase {
    fun execute(request: AttendanceQrCodeCommand)
}