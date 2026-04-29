package finda.findavolunteer.adapter.`in`.qrcode

import finda.findavolunteer.adapter.`in`.qrcode.dto.request.AttendanceQrCodeRequest
import finda.findavolunteer.adapter.`in`.qrcode.dto.request.AttendanceStudentsRequest
import finda.findavolunteer.adapter.`in`.qrcode.dto.request.CreateQrCodeRequest
import finda.findavolunteer.adapter.`in`.qrcode.mapper.toCommand
import finda.findavolunteer.application.service.qrcode.AttendanceQrCodeService
import finda.findavolunteer.application.service.qrcode.AttendanceStudentService
import finda.findavolunteer.application.service.qrcode.CreateQrCodeService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Tag(name = "QR 코드", description = "봉사활동 QR 코드 생성 및 출결 처리 API")
@RestController
@RequestMapping("/qr-codes")
class QrCodeWebAdapter(
    private val createQrCodeService: CreateQrCodeService,
    private val attendanceQrCodeService: AttendanceQrCodeService,
    private val attendanceStudentService: AttendanceStudentService
) {
    @Operation(summary = "QR 코드 생성", description = "봉사활동 출결용 QR 코드를 생성합니다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createQrCode(@RequestBody request: CreateQrCodeRequest) =
        createQrCodeService.execute(request.toCommand())

    @Operation(summary = "QR 코드 출결 처리", description = "학생이 QR 코드를 스캔하여 봉사활동 출결을 처리합니다.")
    @PostMapping("/attendance")
    @ResponseStatus(HttpStatus.OK)
    fun attendByQrCode(@RequestBody request: AttendanceQrCodeRequest) =
        attendanceQrCodeService.execute(request.toCommand())

    @Operation(summary = "학생 일괄 출결 처리", description = "선생님이 학생 목록을 직접 선택하여 일괄 출결 처리합니다.")
    @PostMapping("/attendance/students")
    @ResponseStatus(HttpStatus.OK)
    fun attendStudents(@RequestBody request: AttendanceStudentsRequest) =
        attendanceStudentService.execute(request.toCommand())
}
