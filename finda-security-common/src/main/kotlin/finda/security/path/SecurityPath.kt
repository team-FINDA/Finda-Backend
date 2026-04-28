package finda.security.path

object SecurityPath {
    val PERMIT_ALL_PATHS = listOf(
        "/auth/reissue",
        "/students/signup",
        "/students/login",
        "/teachers/signup",
        "/teachers/login",
        "/students/send-verification",
        "/students/verify-email",
        "/email/**",
        "/swagger-ui/**",
        "/v3/api-docs/**"
    )
}
