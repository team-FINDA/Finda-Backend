package finda.findavolunteer.application.port.out.volunteer

import finda.findavolunteer.domain.volunteer.model.VolunteerRecord

interface VolunteerRecordCommandPort {
    fun save(volunteerRecord: VolunteerRecord): VolunteerRecord
}
