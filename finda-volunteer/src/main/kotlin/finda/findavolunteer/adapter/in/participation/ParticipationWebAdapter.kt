package finda.findavolunteer.adapter.`in`.participation

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "참가", description = "봉사활동 참가 QR 코드 생성 API")
@RestController
@RequestMapping("/participations")
class ParticipationWebAdapter()
