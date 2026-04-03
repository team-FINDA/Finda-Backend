package finda.findavolunteer.adapter.`in`.activity

import finda.findavolunteer.adapter.`in`.activity.dto.request.CreateUserActivityRequest
import finda.findavolunteer.application.port.`in`.activity.CreateUserActivityUseCase
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/activities")
class ActivityWebAdapter(
    private val createUserActivityUseCase: CreateUserActivityUseCase
) {
    @PostMapping("/user")
    fun createUserActivity(
        @Valid @RequestBody
        request: CreateUserActivityRequest
    ) =
        createUserActivityUseCase.execute(request)
}
