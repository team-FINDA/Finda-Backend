package finda.findavolunteer.global.error.exception

enum class ErrorCode(
    private val status: Int,
    private val message: String,
    private val sequence: Int
) : ErrorProperty {

    // server error
    INTERNAL_SERVER_ERROR(500, "Internal Server Error",1),

    // client error
    BAD_REQUEST(400, "Bad Request",1),

    FORBIDDEN(403, "Can Not Access", 1)
    ;

    override fun status(): Int = status
    override fun message(): String = message
    override fun code(): String = "VOLUNTEER-$status-$sequence"
}
