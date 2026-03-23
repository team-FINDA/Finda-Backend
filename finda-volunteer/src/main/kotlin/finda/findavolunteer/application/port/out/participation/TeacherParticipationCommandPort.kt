package finda.findavolunteer.application.port.out.participation

import finda.findavolunteer.domain.participation.model.TeacherParticipation

interface TeacherParticipationCommandPort {
    fun save(teacherParticipation: TeacherParticipation) :TeacherParticipation
}
