package finda.findagateway.domain.model

import java.util.UUID

data class UserInfo(
    val id: UUID,
    val userType: UserType
)
