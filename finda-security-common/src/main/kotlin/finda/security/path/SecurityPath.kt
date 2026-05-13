package finda.security.path

object SecurityPath {
    val PERMIT_ALL_PATHS = listOf(
        "/auth/reissue",

        "/teachers/send-verification",
        "/teachers/verify-email",
        "/teachers/signup",
        "/teachers/login",
        "/teachers/verify",

        "/students/signup",
        "/students/login",
        "/students/send-verification",
        "/students/verify-email",

        "/swagger-ui/**",
        "/v3/api-docs/**"
    )
}
