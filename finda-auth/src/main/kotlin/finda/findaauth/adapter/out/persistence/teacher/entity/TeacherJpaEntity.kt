package finda.findaauth.adapter.out.persistence.teacher.entity

import finda.findaauth.adapter.out.persistence.BaseEntity
import finda.findaauth.adapter.out.persistence.user.entity.UserJpaEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "tbl_teacher")
class TeacherJpaEntity(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    val user: UserJpaEntity?

) : BaseEntity()
