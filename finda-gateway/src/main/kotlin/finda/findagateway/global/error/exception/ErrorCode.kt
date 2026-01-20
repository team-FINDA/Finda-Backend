package finda.findagateway.global.error.exception

enum class ErrorCode(
    private val status: Int,
    private val message: String,
    private val sequence: Int
) : ErrorProperty {

    // jwt
    INVALID_TOKEN(401, "Invalid Token", 1),
    EXPIRED_TOKEN(401, "Expired Token", 2),
    UNEXPECTED_TOKEN(401, "Unexpected Token", 3),

    FORBIDDEN(403, "Can Not Access", 1)
    ;

    override fun status(): Int = status
    override fun message(): String = message
    override fun code(): String = "GATEWAY-$status-$sequence"
}
