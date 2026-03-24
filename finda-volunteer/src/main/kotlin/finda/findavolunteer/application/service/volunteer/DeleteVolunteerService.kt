package finda.findavolunteer.application.service.volunteer

import finda.findavolunteer.application.exception.VolunteerForbiddenException
import finda.findavolunteer.application.facade.UserFacade
import finda.findavolunteer.application.port.`in`.volunteer.DeleteVolunteerUseCase
import finda.findavolunteer.application.port.out.volunteer.VolunteerCommandPort
import finda.findavolunteer.application.port.out.volunteer.VolunteerQueryPort
import org.springframework.stereotype.Service
import java.util.*

@Service
class DeleteVolunteerService(
    private val userFacade: UserFacade,
    private val volunteerQueryPort: VolunteerQueryPort,
    private val volunteerCommandPort: VolunteerCommandPort
): DeleteVolunteerUseCase {
    override fun execute(volunteerId: UUID) {
        val userId = userFacade.currentUserId()
        val volunteer = volunteerQueryPort.findByIdOrThrow(volunteerId)
        if (!checkUser(userId, volunteer.userId)) throw VolunteerForbiddenException

        volunteerCommandPort.deleteById(volunteerId)
    }

    private fun checkUser(userId: UUID, volunteerUserId: UUID) = userId == volunteerUserId


}