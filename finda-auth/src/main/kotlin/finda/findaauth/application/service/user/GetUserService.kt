package finda.findaauth.application.service.user

import finda.findaauth.application.exception.user.UserNotFoundException
import finda.findaauth.application.port.out.user.UserQueryPort
import finda.findaauth.domain.user.model.User
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GetUserService(
    private val userQueryPort: UserQueryPort
) {
    fun getById(userId: UUID): User {
        return userQueryPort.findById(userId)
            ?: throw UserNotFoundException
    }
}
