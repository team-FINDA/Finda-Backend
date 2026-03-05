package finda.findaauth.application.port.out.user

import finda.findaauth.domain.user.model.User
import java.util.UUID

interface UserQueryPort {
    fun findByEmail(email: String): User?
    fun existsByEmail(email: String): Boolean
    fun findById(id: UUID): User?
}
