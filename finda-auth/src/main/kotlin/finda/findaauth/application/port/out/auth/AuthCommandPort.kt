package finda.findaauth.application.port.out.auth

interface AuthCommandPort {
    fun save(token: String)
}
