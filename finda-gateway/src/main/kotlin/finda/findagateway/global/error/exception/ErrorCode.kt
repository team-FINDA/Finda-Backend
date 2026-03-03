package finda.findagateway.global.error.exception

enum class ErrorCode(
    private val status: Int,
    private val message: String,
    private val sequence: Int
) : ErrorProperty {

    // jwt
    INVALID_TOKEN(401, "Invalid Token", 1),
    EXPIRED_TOKEN(401, "Expired Token", 2);

    override fun status(): Int = status
    override fun message(): String = message
    override fun code(): String = "GATEWAY-$status-$sequence"
}
