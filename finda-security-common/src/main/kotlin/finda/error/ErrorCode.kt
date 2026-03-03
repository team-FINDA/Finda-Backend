package finda.error

enum class ErrorCode(
    private val status: Int,
    private val message: String,
    private val sequence: Int
) : ErrorProperty {

    // jwt
    INVALID_TOKEN(401, "Invalid Token", 1),
    EXPIRED_TOKEN(401, "Expired Token", 2),
    UNEXPECTED_TOKEN(401, "Unexpected Token", 3),

    // passport
    INVALID_PASSPORT(401, "Invalid Passport", 4),
    INVALID_PASSPORT_INTEGRITY(401, "Invalid Passport Integrity", 5),
    PASSPORT_ISSUED_IN_FUTURE(401, "Passport Issued In Future", 6),
    PASSPORT_EXPIRED(401, "Passport Expired", 7),

    FORBIDDEN(403, "Can Not Access", 1);

    override fun status(): Int = status
    override fun message(): String = message
    override fun code(): String = "GATEWAY-$status-$sequence"
}
