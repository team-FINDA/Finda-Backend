package finda.findavolunteer.application.port.out.participation

import finda.findavolunteer.domain.participation.model.StudentParticipation

interface StudentParticipationCommandPort {
    fun save(studentParticipation: StudentParticipation): StudentParticipation
}
