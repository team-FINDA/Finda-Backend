package finda.findaauth.domain.user.model

import java.util.UUID

data class UserInfo(
    val id: UUID,
    val userType: UserType
)
