package finda.findavolunteer.adapter.`in`.qrcode.mapper

import finda.findavolunteer.adapter.`in`.qrcode.dto.request.AttendanceQrCodeRequest
import finda.findavolunteer.adapter.`in`.qrcode.dto.request.AttendanceStudentsRequest
import finda.findavolunteer.adapter.`in`.qrcode.dto.request.CreateQrCodeRequest
import finda.findavolunteer.application.port.`in`.qrcode.dto.request.AttendanceQrCodeCommand
import finda.findavolunteer.application.port.`in`.qrcode.dto.request.AttendanceStudentsCommand
import finda.findavolunteer.application.port.`in`.qrcode.dto.request.CreateQrCodeCommand

fun CreateQrCodeRequest.toCommand(): CreateQrCodeCommand =
    CreateQrCodeCommand(
        volunteerId = volunteerId
    )

fun AttendanceQrCodeRequest.toCommand(): AttendanceQrCodeCommand =
    AttendanceQrCodeCommand(
        qrCode = qrCode
    )

fun AttendanceStudentsRequest.toCommand(): AttendanceStudentsCommand =
    AttendanceStudentsCommand(
        userIds = userIds,
        volunteerId = volunteerId
    )
