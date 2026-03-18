package finda.findanotification.application.port.`in`.notice.dto.response

import finda.findanotification.domain.notice.model.Notice
import finda.findanotification.domain.notice.type.Status
import java.time.LocalDate
import java.time.LocalTime

data class GetAllNoticesResult(
    val title: String,
    val body: String,
    val status: Status,
    val noticeDate: LocalDate?,
    val noticeTime: LocalTime?
) {
    companion object {
        fun from(notice: Notice): GetAllNoticesResult {
            return GetAllNoticesResult(
                title = notice.title,
                body = notice.body,
                status = notice.status,
                noticeDate = notice.noticeDate,
                noticeTime = notice.noticeTime
            )
        }
    }
}
