package finda.findavolunteer.application.service.volunteer

import finda.findavolunteer.application.port.out.volunteer.VolunteerQueryPort
import finda.findavolunteer.domain.volunteer.model.Volunteer
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GetVolunteerService(
    private val volunteerQueryPort: VolunteerQueryPort
) {
    fun getAllRemindTimes(): List<Volunteer> {
        return volunteerQueryPort.findAllWithRemindTime()
    }

    fun getTopActivitiesByVolunteerTime(userId: UUID): List<String> {
        return volunteerQueryPort.findTopActivitiesByUserId(userId)
    }
}
