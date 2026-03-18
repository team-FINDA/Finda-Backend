package finda.findanotification.domain.notice.model

import finda.findanotification.domain.notice.type.Status
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class Notice(
    val id: UUID = UUID(0, 0),
    var title: String,
    var body: String,
    val userId: UUID,
    var status: Status,
    var noticeDate: LocalDate,
    var noticeTime: LocalTime
) {
    fun update(
        title: String,
        body: String,
        noticeDate: LocalDate,
        noticeTime: LocalTime
    ) {
        this.title = title
        this.body = body
        this.noticeDate = noticeDate
        this.noticeTime = noticeTime
    }
}
