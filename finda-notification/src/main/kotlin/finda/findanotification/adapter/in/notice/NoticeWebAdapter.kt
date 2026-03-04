package finda.findanotification.adapter.`in`.notice

import finda.findanotification.adapter.`in`.notice.dto.request.CreateNoticeWebRequest
import finda.findanotification.application.port.`in`.notice.CreateNoticeUseCase
import finda.findanotification.application.port.`in`.notice.dto.request.CreateNoticeCommand
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/notice")
class NoticeWebAdapter(
    private val createNoticeUseCase: CreateNoticeUseCase
) {

    @PostMapping
    fun createNotice(@RequestBody request: CreateNoticeWebRequest) {
        createNoticeUseCase.execute(
            CreateNoticeCommand(
                title = request.title,
                body = request.body,
                noticeDate = request.noticeDate,
                noticeTime = request.noticeTime
            )
        )
    }
}
