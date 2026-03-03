package finda.security.passport.model

import java.util.UUID

data class Passport(
    val userId: UUID,
    val authority: Authority,
    val userIntegrity: String
)
