package finda.findavolunteer.application.port.out.user

import java.util.UUID

interface UserQueryPort {
    fun getUserName(userId: UUID): String?
}
