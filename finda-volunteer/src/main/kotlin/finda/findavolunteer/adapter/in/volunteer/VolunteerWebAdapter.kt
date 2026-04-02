package finda.findavolunteer.adapter.`in`.volunteer

import finda.findavolunteer.adapter.`in`.volunteer.dto.request.CreateVolunteerRequest
import finda.findavolunteer.adapter.`in`.volunteer.dto.response.VolunteerDetailResponse
import finda.findavolunteer.adapter.`in`.volunteer.dto.response.VolunteerListResponse
import finda.findavolunteer.application.port.`in`.volunteer.CreateVolunteerUseCase
import finda.findavolunteer.application.port.`in`.volunteer.DeleteVolunteerUseCase
import finda.findavolunteer.application.port.`in`.volunteer.VolunteerDetailUseCase
import finda.findavolunteer.application.port.`in`.volunteer.VolunteerListUseCase
import finda.findavolunteer.domain.volunteer.enum.VolunteerStatus
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
    val volunteerDetailUseCase: VolunteerDetailUseCase,
    val volunteerListUseCase: VolunteerListUseCase
) {
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    fun createVolunteer(@RequestBody request: CreateVolunteerRequest) = createVolunteerUseCase.execute(request)

    @GetMapping("/{volunteerId}")
    @ResponseStatus(value = HttpStatus.OK)
    fun getVolunteer(@PathVariable volunteerId: UUID): VolunteerDetailResponse =
        volunteerDetailUseCase.execute(volunteerId)

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    fun getVolunteers(
        @RequestParam(required = false) status: VolunteerStatus?,
        @RequestParam(required = false) year: Int?,
        @RequestParam(required = false) sortBy: String?
    ): List<VolunteerListResponse> = volunteerListUseCase.execute(
        status = status,
        year = year,
        sortBy = sortBy
    )

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.OK)
    fun deleteVolunteer(@RequestParam volunteerId: UUID) = deleteVolunteerUseCase.execute(volunteerId)
}
