package finda.findavolunteer.adapter.`in`.volunteer

import finda.findavolunteer.adapter.`in`.volunteer.dto.request.CreateVolunteerRequest
import finda.findavolunteer.application.service.volunteer.CreateVolunteerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/volunteer")
class VolunteerController(
    val createVolunteerService: CreateVolunteerService
) {
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    fun createVolunteer(@RequestBody request: CreateVolunteerRequest) = createVolunteerService.execute(request)
}
