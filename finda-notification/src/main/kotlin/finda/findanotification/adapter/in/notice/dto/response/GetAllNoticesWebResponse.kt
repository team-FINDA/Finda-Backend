package finda.findanotification.adapter.`in`.notice.dto.response

import finda.findanotification.application.port.`in`.notice.dto.response.GetAllNoticesResult
import finda.findanotification.domain.notice.type.Status
import java.time.LocalDate
import java.time.LocalTime

data class GetAllNoticesWebResponse(
    val title: String,
    val body: String,
    val status: Status,
    val noticeDate: LocalDate?,
    val noticeTime: LocalTime?
) {
    companion object {
        fun from(result: GetAllNoticesResult): GetAllNoticesWebResponse {
            return GetAllNoticesWebResponse(
                title = result.title,
                body = result.body,
                status = result.status,
                noticeDate = result.noticeDate,
                noticeTime = result.noticeTime
            )
        }
    }
}
