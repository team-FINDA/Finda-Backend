package finda.findavolunteer.adapter.`in`.qrcode

import finda.findavolunteer.adapter.`in`.qrcode.dto.request.AttendanceQrCodeRequest
import finda.findavolunteer.adapter.`in`.qrcode.dto.request.AttendanceStudentsRequest
import finda.findavolunteer.adapter.`in`.qrcode.dto.request.CreateQrCodeRequest
import finda.findavolunteer.adapter.`in`.qrcode.mapper.toCommand
import finda.findavolunteer.application.service.qrcode.AttendanceQrCodeService
import finda.findavolunteer.application.service.qrcode.AttendanceStudentService
import finda.findavolunteer.application.service.qrcode.CreateQrCodeService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/qr-codes")
class QrCodeWebAdapter(
    private val createQrCodeService: CreateQrCodeService,
    private val attendanceQrCodeService: AttendanceQrCodeService,
    private val attendanceStudentService: AttendanceStudentService
) {
    @PostMapping
    fun createQrCode(@RequestBody request: CreateQrCodeRequest) =
        createQrCodeService.execute(request.toCommand())

    @PostMapping("/attendance")
    fun attendByQrCode(@RequestBody request: AttendanceQrCodeRequest) =
        attendanceQrCodeService.execute(request.toCommand())

    @PostMapping("/attendance/students")
    fun attendStudents(@RequestBody request: AttendanceStudentsRequest) =
        attendanceStudentService.execute(request.toCommand())
}
