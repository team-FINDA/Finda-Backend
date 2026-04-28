package finda.findavolunteer.adapter.`in`.activity

import finda.findavolunteer.adapter.`in`.activity.dto.request.CreateUserActivityRequest
import finda.findavolunteer.adapter.`in`.activity.mapper.toCommand
import finda.findavolunteer.application.port.`in`.activity.CreateUserActivityUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "활동", description = "사용자 봉사활동 이력 등록 API")
@RestController
@RequestMapping("/activities")
class ActivityWebAdapter(
    private val createUserActivityUseCase: CreateUserActivityUseCase
) {
    @Operation(summary = "학생 봉사활동 봉사 역할 등록", description = "학생의 봉사활동 봉사 역할을 등록합니다.")
    @PostMapping("/user")
    fun createUserActivity(
        @Valid @RequestBody
        request: CreateUserActivityRequest
    ) =
        createUserActivityUseCase.execute(request.toCommand())
}
