package finda.findaauth.application.port.out.teacher

interface TeacherPreAuthQueryPort {
    fun isValid(token: String): Boolean
}
