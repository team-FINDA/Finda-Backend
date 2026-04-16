package finda.findavolunteer.application.port.`in`.qrcode

import finda.findavolunteer.application.port.`in`.qrcode.dto.request.AttendanceStudentsCommand

interface AttendanceStudentsUseCase {
    fun execute(request: AttendanceStudentsCommand)
}
