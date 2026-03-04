package finda.findaauth.application.exception.devicetoken

import finda.findaauth.global.error.exception.ErrorCode
import finda.findaauth.global.error.exception.FindaException

object DeviceTokenNotFoundException : FindaException(ErrorCode.DEVICE_TOKEN_NOT_FOUND)
