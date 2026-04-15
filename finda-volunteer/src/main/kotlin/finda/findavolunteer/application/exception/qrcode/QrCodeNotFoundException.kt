package finda.findavolunteer.application.exception.qrcode

import finda.findavolunteer.global.error.exception.ErrorCode
import finda.findavolunteer.global.error.exception.FindaException

object QrCodeNotFoundException : FindaException(
    ErrorCode.QRCODE_NOT_FOUND
)
