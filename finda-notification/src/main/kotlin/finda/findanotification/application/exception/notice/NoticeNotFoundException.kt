package finda.findanotification.application.exception.notice

import finda.findanotification.global.error.exception.ErrorCode
import finda.findanotification.global.error.exception.FindaException

object NoticeNotFoundException : FindaException(ErrorCode.NOTICE_NOT_FOUND)
