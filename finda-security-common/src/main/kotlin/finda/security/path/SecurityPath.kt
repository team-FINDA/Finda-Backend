package finda.security.path

object SecurityPath {
    val PERMIT_ALL_PATHS = listOf(
        "/auth/test",
        "/auth/reissue",
        "/students/signup",
        "/students/login",
        "/teachers/signup",
        "/teachers/login",
        "/teachers/verify",
        "/teachers/verify-email",
        "/students/send-verification",
        "/teachers/send-verification",
        "/students/verify-email",
        "/email/**"
    )
}
