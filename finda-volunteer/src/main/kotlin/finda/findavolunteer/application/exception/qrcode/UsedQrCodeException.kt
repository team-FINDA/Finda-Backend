package finda.findavolunteer.application.exception.qrcode

import finda.findavolunteer.global.error.exception.ErrorCode
import finda.findavolunteer.global.error.exception.FindaException

object UsedQrCodeException: FindaException(
    ErrorCode.USED_QRCODE
)