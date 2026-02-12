package finda.findanotification.adapter.out.persistence.notice.entity

import finda.findanotification.adapter.out.persistence.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
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

    @Column(name = "admin_id", nullable = false)
    val adminId: String,

    @Column(name = "notice_date", nullable = false)
    val noticeDate: LocalDate,

    @Column(name = "notice_time", nullable = false)
    val noticeTime: LocalTime
) : BaseEntity(id)
