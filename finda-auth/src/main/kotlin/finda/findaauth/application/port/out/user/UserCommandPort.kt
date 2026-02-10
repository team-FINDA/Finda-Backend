package finda.findaauth.application.port.out.user

import finda.findaauth.domain.user.model.User

interface UserCommandPort {
    fun save(user: User): User
}
