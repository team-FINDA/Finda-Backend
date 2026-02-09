package finda.findaauth.application.port.out.student

import finda.findaauth.domain.student.model.Student

interface StudentCommandPort {
    fun save(student: Student): Student
}
