package finda.findaauth.domain.user.model

import finda.security.passport.model.Authority
import java.time.LocalDateTime
import java.util.UUID

data class User(
    val id: UUID = UUID(0, 0),
    val name: String,
    val email: String,
    val password: String,
    val deletedAt: LocalDateTime? = null,
    val authority: Authority
)
