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
    UNAUTHORIZED(401, "Unauthorized", 3),

    // user
    USER_NOT_FOUND(404, "User Not Found", 1),
    EMAIL_ALREADY_EXISTS(409, "Email Already Exists", 1),

    // auth
    INVALID_SIGNUP_SECRET(401, "Invalid Signup Secret",4),
    INVALID_PRE_AUTH_TOKEN(401, "Invalid Pre Auth Token",5),
    INVALID_CREDENTIALS(401, "Invalid email or password",6),

    // email verification
    VERIFICATION_CODE_NOT_FOUND(404, "Verification Code Not Found", 2),
    VERIFICATION_CODE_MISMATCH(400, "Verification Code Mismatch", 2),
    EMAIL_NOT_VERIFIED(400, "Email Not Verified", 3),
    EMAIL_SEND_LIMIT_EXCEEDED(429, "Email Send Limit Exceeded", 1);

    override fun status(): Int = status
    override fun message(): String = message
    override fun code(): String = "AUTH-$status-$sequence"
}
