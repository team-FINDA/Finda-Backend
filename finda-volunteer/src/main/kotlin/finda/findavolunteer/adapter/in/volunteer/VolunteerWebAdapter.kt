package finda.findavolunteer.adapter.`in`.volunteer

import finda.findavolunteer.adapter.`in`.volunteer.dto.request.CreateVolunteerRequest
import finda.findavolunteer.adapter.`in`.volunteer.dto.response.VolunteerDetailResponse
import finda.findavolunteer.application.port.`in`.volunteer.CreateVolunteerUseCase
import finda.findavolunteer.application.port.`in`.volunteer.DeleteVolunteerUseCase
import finda.findavolunteer.application.port.`in`.volunteer.VolunteerDetailUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/volunteers")
class VolunteerWebAdapter(
    val createVolunteerUseCase: CreateVolunteerUseCase,
    val deleteVolunteerUseCase: DeleteVolunteerUseCase,
    val volunteerDetailUseCase: VolunteerDetailUseCase
) {
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    fun createVolunteer(@RequestBody request: CreateVolunteerRequest) = createVolunteerUseCase.execute(request)

    @GetMapping("/{volunteerId}")
    fun getVolunteer(@PathVariable volunteerId: UUID): VolunteerDetailResponse =
        volunteerDetailUseCase.execute(volunteerId)

    @DeleteMapping
    fun deleteVolunteer(@RequestParam volunteerId: UUID) = deleteVolunteerUseCase.execute(volunteerId)
}
