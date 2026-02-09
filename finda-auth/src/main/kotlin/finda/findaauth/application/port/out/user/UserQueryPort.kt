package finda.findaauth.application.port.out.user

interface UserQueryPort {
    fun existsByEmail(email: String): Boolean
}
