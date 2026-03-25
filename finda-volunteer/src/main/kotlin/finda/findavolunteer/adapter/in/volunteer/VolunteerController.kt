package finda.findavolunteer.adapter.`in`.volunteer

import finda.findavolunteer.adapter.`in`.volunteer.dto.request.CreateVolunteerRequest
import finda.findavolunteer.application.port.`in`.volunteer.CreateVolunteerUseCase
import finda.findavolunteer.application.port.`in`.volunteer.DeleteVolunteerUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/volunteers")
class VolunteerController(
    val createVolunteerUseCase: CreateVolunteerUseCase,
    val deleteVolunteerUseCase: DeleteVolunteerUseCase
) {
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    fun createVolunteer(@RequestBody request: CreateVolunteerRequest) = createVolunteerUseCase.execute(request)

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun deleteVolunteer(@RequestParam volunteerId: UUID) = deleteVolunteerUseCase.execute(volunteerId)
}
