package finda.findavolunteer.application.service.volunteer

import finda.findavolunteer.application.port.out.volunteer.VolunteerQueryPort
import finda.findavolunteer.domain.volunteer.model.Volunteer
import org.springframework.stereotype.Service

@Service
class GetVolunteerService(
    private val volunteerQueryPort: VolunteerQueryPort
) {
    fun getAllRemindTimes(): List<Volunteer> {
        return volunteerQueryPort.findAllWithRemindTime()
    }
}
