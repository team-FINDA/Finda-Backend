package finda.findaauth.application.port.out.user

import finda.findaauth.domain.user.model.User

interface UserQueryPort {
    fun findByEmail(email: String): User?
    fun existsByEmail(email: String): Boolean
}
