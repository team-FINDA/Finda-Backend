package finda.findavolunteer.adapter.`in`.participation

import finda.findavolunteer.adapter.`in`.participation.dto.request.CreateQrCodeRequest
import finda.findavolunteer.adapter.`in`.participation.mapper.toCommand
import finda.findavolunteer.application.service.qrcode.CreateQrCodeService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/participations")
class ParticipationWebAdapter(
    private val createQrCodeService: CreateQrCodeService
) {
    @PostMapping("/qr/create")
    fun createQr(request: CreateQrCodeRequest) = createQrCodeService.execute(request.toCommand())
}
