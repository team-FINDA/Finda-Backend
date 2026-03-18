package finda.findanotification.adapter.`in`.notice.dto.response

import finda.findanotification.application.port.`in`.notice.dto.response.GetNoticeResult
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class GetNoticeWebResponse(
    val userName: UUID,
    val title: String,
    val body: String,
    val noticeDate: LocalDate?,
    val noticeTime: LocalTime?
) {
    companion object {
        fun from(result: GetNoticeResult): GetNoticeWebResponse {
            return GetNoticeWebResponse(
                userName = result.userName,
                title = result.title,
                body = result.body,
                noticeDate = result.noticeDate,
                noticeTime = result.noticeTime
            )
        }
    }
}
