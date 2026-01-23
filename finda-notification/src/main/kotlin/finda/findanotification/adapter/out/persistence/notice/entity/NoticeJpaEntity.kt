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

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val body: String,

    @Column(nullable = false)
    val adminId: String
) : BaseEntity(id)
