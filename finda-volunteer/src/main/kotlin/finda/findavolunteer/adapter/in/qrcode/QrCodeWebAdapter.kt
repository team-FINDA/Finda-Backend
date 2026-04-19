package finda.findavolunteer.adapter.`in`.qrcode

import finda.findavolunteer.adapter.`in`.qrcode.dto.request.AttendanceQrCodeRequest
import finda.findavolunteer.adapter.`in`.qrcode.dto.request.AttendanceStudentsRequest
import finda.findavolunteer.adapter.`in`.qrcode.dto.request.CreateQrCodeRequest
import finda.findavolunteer.adapter.`in`.qrcode.mapper.toCommand
import finda.findavolunteer.application.service.qrcode.AttendanceQrCodeService
import finda.findavolunteer.application.service.qrcode.AttendanceStudentService
import finda.findavolunteer.application.service.qrcode.CreateQrCodeService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/qr-codes")
class QrCodeWebAdapter(
    private val createQrCodeService: CreateQrCodeService,
    private val attendanceQrCodeService: AttendanceQrCodeService,
    private val attendanceStudentService: AttendanceStudentService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createQrCode(@RequestBody request: CreateQrCodeRequest) =
        createQrCodeService.execute(request.toCommand())

    @PostMapping("/attendance")
    @ResponseStatus(HttpStatus.OK)
    fun attendByQrCode(@RequestBody request: AttendanceQrCodeRequest) =
        attendanceQrCodeService.execute(request.toCommand())

    @PostMapping("/attendance/students")
    @ResponseStatus(HttpStatus.OK)
    fun attendStudents(@RequestBody request: AttendanceStudentsRequest) =
        attendanceStudentService.execute(request.toCommand())
}
