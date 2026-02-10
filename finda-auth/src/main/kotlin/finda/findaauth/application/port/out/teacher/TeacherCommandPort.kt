package finda.findaauth.application.port.out.teacher

import finda.findaauth.domain.teacher.model.Teacher

interface TeacherCommandPort {
    fun save(teacher: Teacher): Teacher
}
