package finda.findavolunteer.application.port.out.qrcode

import finda.findavolunteer.domain.qrcode.model.QrCode

interface QrCodeCommandPort {
    fun save(qrCode: QrCode): QrCode
}
