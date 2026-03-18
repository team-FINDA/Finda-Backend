package finda.findanotification.application.port.`in`.notice

import finda.findanotification.application.port.`in`.notice.dto.response.GetAllNoticesResult

interface GetAllNoticesUseCase {
    fun execute(): List<GetAllNoticesResult>
}
