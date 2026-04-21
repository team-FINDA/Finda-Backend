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
    TEACHER_PARTICIPATION_FORBIDDEN(403, "Teacher Participation Forbidden", 2),
    STUDENT_PARTICIPATION_FORBIDDEN(403, "Student Participation Forbidden", 3),

    VOLUNTEER_NOT_FOUND(404, "Volunteer Not Found", 1),
    VOLUNTEER_RECORD_NOT_FOUND(404, "Volunteer Record Not Found", 6),
    TEACHER_PARTICIPATION_NOT_FOUND(404, "Teacher Participation Not Found", 2),
    USER_NOT_FOUND(404, "User Not Found", 3),
    ACTIVITY_NOT_FOUND(404, "Activity Not Found", 4),
    QRCODE_NOT_FOUND(404, "QRCode Not Found", 5),

    USED_QRCODE(409, "Used QrCode", 1);

    override fun status(): Int = status
    override fun message(): String = message
    override fun code(): String = "VOLUNTEER-$status-$sequence"
}
