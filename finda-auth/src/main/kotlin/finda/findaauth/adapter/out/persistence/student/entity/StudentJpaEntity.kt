package finda.findaauth.adapter.out.persistence.student.entity

import finda.findaauth.adapter.out.persistence.BaseEntity
import finda.findaauth.adapter.out.persistence.user.entity.UserJpaEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(
    name = "tbl_student",
    uniqueConstraints =
    [
        UniqueConstraint(
            name = "STUDENT_NUMBER_UNIQUE",
            columnNames = ["grade", "class_num", "num"]
        )
    ]
)
class StudentJpaEntity(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    val user: UserJpaEntity?,

    @Column(name = "grade", nullable = false)
    val grade: Int,

    @Column(name = "class_num", nullable = false)
    val classNum: Int,

    @Column(name = "num", nullable = false)
    val num: Int,

    @Column(name = "total_volunteer_time", nullable = false)
    val totalVolunteerTime: Int,

    @Column(name = "deleted_at", nullable = true)
    val deletedAt: LocalDateTime? = null
) : BaseEntity()
