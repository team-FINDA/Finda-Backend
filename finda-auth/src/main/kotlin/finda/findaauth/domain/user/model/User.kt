package finda.findaauth.domain.user.model

import java.util.UUID

data class User(
    val id: UUID?,
    val name: String,
    val email: String,
    val password: String
)
