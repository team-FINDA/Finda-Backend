package finda.findavolunteer.global.error.exception

enum class ErrorCode(
    private val status: Int,
    private val message: String,
    private val sequence: Int
) : ErrorProperty {

    // server error
    INTERNAL_SERVER_ERROR(500, "Internal Server Error", 1),

    // client error
    BAD_REQUEST(400, "Bad Request", 1),
    INVALID_VOLUNTEER_SORT_BY(400, "Invalid Volunteer Sort By", 2),

    VOLUNTEER_FORBIDDEN(403, "Volunteer Forbidden", 1),

    VOLUNTEER_NOT_FOUND(404, "Volunteer Not Found", 1),
    TEACHER_PARTICIPATION_NOT_FOUND(404, "Teacher Participation Not Found", 2),
    USER_NOT_FOUND(404, "User Not Found", 3);

    override fun status(): Int = status
    override fun message(): String = message
    override fun code(): String = "VOLUNTEER-$status-$sequence"
}
