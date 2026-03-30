package finda.findavolunteer.application.port.out.qrcode

import finda.findavolunteer.domain.qrcode.model.QrCode
import java.util.UUID

interface QrCodeQueryPort {
    fun findById(id: UUID): QrCode?
}
