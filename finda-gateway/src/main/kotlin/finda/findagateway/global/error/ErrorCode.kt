package finda.findagateway.global.error

enum class ErrorCode(
    private val status: Int,
    private val message: String,
    private val sequence: Int
) : ErrorProperty {

    // jwt
    INVALID_TOKEN(401, "Invalid Token", 1),
    EXPIRED_TOKEN(401, "Expired Token", 2),

    INTERNAL_SERVER_ERROR(500, "Internal Server", 1);

    override fun status(): Int = status
    override fun message(): String = message
    override fun code(): String = "GATEWAY-$status-$sequence"
}
