package finda.findanotification.application.port.`in`.notice

import finda.findanotification.adapter.`in`.notice.dto.request.NoticeWebRequest

interface GetAllNoticesUseCase {
    fun execute(): List<NoticeWebRequest>
}
