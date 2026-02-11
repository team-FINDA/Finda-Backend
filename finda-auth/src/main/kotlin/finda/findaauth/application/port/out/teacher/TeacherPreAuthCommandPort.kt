package finda.findaauth.application.port.out.teacher

interface TeacherPreAuthCommandPort {
    fun save(token: String)

    fun delete(token: String)
}
