package finda.findanotification.adapter.out.persistence.notice.entity

import finda.findanotification.adapter.out.persistence.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
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
    val adminId: String
) : BaseEntity(id)
