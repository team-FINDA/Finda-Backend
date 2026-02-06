package finda.findaauth.global.error.exception

enum class ErrorCode(
    private val status: Int,
    private val message: String,
    private val sequence: Int
) : ErrorProperty {

    // server error
    INTERNAL_SERVER_ERROR(500, "Internal Server Error", 1),

    // client error
    BAD_REQUEST(400, "Bad Request", 1),

    // jwt
    INVALID_TOKEN(401, "Invalid Token", 1),
    EXPIRED_TOKEN(401, "Expired Token", 2),

    // user
    USER_NOT_FOUND(404, "User Not Found", 1),

    // auth
    INVALID_SIGNUP_SECRET(401, "Invalid Signup Secret", 1);

    override fun status(): Int = status
    override fun message(): String = message
    override fun code(): String = "AUTH-$status-$sequence"
}
