package finda.findanotification.adapter.out.persistence.notice.entity

import finda.findanotification.adapter.out.persistence.BaseEntity
import finda.findanotification.domain.notice.type.Status
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

@Entity
@Table(name = "tbl_notice")
class NoticeJpaEntity(
    id: UUID?,

    @Column(name = "title", nullable = false)
    val title: String,

    @Column(name = "body", nullable = false)
    val body: String,

    @Column(name = "user_id", nullable = false)
    val userId: UUID,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val status: Status,

    @Column(name = "notice_date", nullable = false)
    val noticeDate: LocalDate,

    @Column(name = "notice_time", nullable = false)
    val noticeTime: LocalTime
) : BaseEntity()
