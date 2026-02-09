package finda.findaauth.application.port.out.auth

interface AuthQueryPort {
    fun isValid(token: String): Boolean
}
