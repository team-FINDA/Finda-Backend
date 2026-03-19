package finda.findanotification.adapter.`in`.notice.dto.response

import finda.findanotification.application.port.`in`.notice.dto.response.NoticeResult
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class NoticeWebResponse(
    val id: UUID,
    val userName: String,
    val title: String,
    val body: String,
    val noticeDate: LocalDate?,
    val noticeTime: LocalTime?
) {
    companion object {
        fun from(result: NoticeResult): NoticeWebResponse {
            return NoticeWebResponse(
                id = result.id,
                userName = result.userName,
                title = result.title,
                body = result.body,
                noticeDate = result.noticeDate,
                noticeTime = result.noticeTime
            )
        }
    }
}
