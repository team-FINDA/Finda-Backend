package finda.findaauth.adapter.out.persistence.user.entity

import finda.findaauth.adapter.out.persistence.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "tbl_user")
class UserJpaEntity(
    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "email", nullable = false, unique = true)
    val email: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Column(name = "deleted_at", nullable = true)
    val deletedAt: LocalDateTime? = null
) : BaseEntity()
