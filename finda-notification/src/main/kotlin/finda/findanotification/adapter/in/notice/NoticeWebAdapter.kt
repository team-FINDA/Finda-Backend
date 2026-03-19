package finda.findanotification.adapter.`in`.notice

import finda.findanotification.adapter.`in`.notice.dto.request.NoticeWebRequest
import finda.findanotification.adapter.`in`.notice.dto.response.GetAllNoticesWebResponse
import finda.findanotification.adapter.`in`.notice.dto.response.NoticeWebResponse
import finda.findanotification.application.port.`in`.notice.CreateNoticeUseCase
import finda.findanotification.application.port.`in`.notice.DeleteNoticeUseCase
import finda.findanotification.application.port.`in`.notice.GetAllNoticesUseCase
import finda.findanotification.application.port.`in`.notice.GetNoticeUseCase
import finda.findanotification.application.port.`in`.notice.UpdateNoticeUseCase
import finda.findanotification.application.port.`in`.notice.dto.request.DeleteNoticeCommand
import finda.findanotification.application.port.`in`.notice.dto.request.NoticeCommand
import finda.findanotification.application.service.user.facade.UserFacade
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/notice")
class NoticeWebAdapter(
    private val createNoticeUseCase: CreateNoticeUseCase,
    private val getNoticeUseCase: GetNoticeUseCase,
    private val getAllNoticesUseCase: GetAllNoticesUseCase,
    private val updateNoticeUseCase: UpdateNoticeUseCase,
    private val deleteNoticeUseCase: DeleteNoticeUseCase,
    private val userFacade: UserFacade
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createNotice(
        @RequestBody @Valid
        request: NoticeWebRequest
    ): NoticeWebResponse {
        return NoticeWebResponse.from(
            createNoticeUseCase.execute(
                NoticeCommand(
                    title = request.title,
                    body = request.body,
                    userId = userFacade.getCurrentUserId(),
                    noticeDate = request.noticeDate,
                    noticeTime = request.noticeTime
                )
            )
        )
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getNotice(
        @PathVariable id: UUID
    ): NoticeWebResponse {
        return NoticeWebResponse.from(
            getNoticeUseCase.execute(id)
        )
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAllNotices(): List<GetAllNoticesWebResponse> {
        return getAllNoticesUseCase.execute()
            .map(GetAllNoticesWebResponse::from)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateNotice(
        @PathVariable id: UUID,
        @Valid @RequestBody
        request: NoticeWebRequest
    ): NoticeWebResponse {
        return NoticeWebResponse.from(
            updateNoticeUseCase.execute(
                id,
                NoticeCommand(
                    title = request.title,
                    body = request.body,
                    userId = userFacade.getCurrentUserId(),
                    noticeDate = request.noticeDate,
                    noticeTime = request.noticeTime
                )
            )
        )
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteNotice(
        @PathVariable id: UUID
    ) {
        deleteNoticeUseCase.execute(
            DeleteNoticeCommand(
                noticeId = id,
                userId = userFacade.getCurrentUserId(),
            )
        )
    }
}
