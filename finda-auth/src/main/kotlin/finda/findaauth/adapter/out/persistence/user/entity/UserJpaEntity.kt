package finda.findaauth.adapter.out.persistence.user.entity

import finda.findaauth.adapter.out.persistence.BaseEntity
import finda.security.passport.model.Authority
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "tbl_user")
class UserJpaEntity(
    id: java.util.UUID? = null,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "email", nullable = false, unique = true)
    val email: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Column(name = "authority", nullable = false)
    @Enumerated(EnumType.STRING)
    val authority: Authority,

    @Column(name = "deleted_at", nullable = true)
    val deletedAt: LocalDateTime? = null
) : BaseEntity(id)
