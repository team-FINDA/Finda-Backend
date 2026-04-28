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
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
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

@Tag(name = "공지", description = "공지 생성, 조회, 수정, 삭제 API")
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

    @Operation(summary = "공지 생성", description = "예약 발송할 공지를 생성합니다. 미래 날짜/시간만 허용됩니다.")
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

    @Operation(summary = "공지 단건 조회", description = "공지 ID로 특정 공지를 조회합니다.")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getNotice(
        @PathVariable id: UUID
    ): NoticeWebResponse {
        return NoticeWebResponse.from(
            getNoticeUseCase.execute(id)
        )
    }

    @Operation(summary = "공지 전체 조회", description = "전체 공지 목록을 조회합니다.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAllNotices(): List<GetAllNoticesWebResponse> {
        return getAllNoticesUseCase.execute()
            .map(GetAllNoticesWebResponse::from)
    }

    @Operation(summary = "공지 수정", description = "본인이 작성한 공지의 내용, 날짜, 시간을 수정합니다.")
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

    @Operation(summary = "공지 삭제", description = "본인이 작성한 공지를 삭제합니다.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteNotice(
        @PathVariable id: UUID
    ) {
        deleteNoticeUseCase.execute(
            DeleteNoticeCommand(
                noticeId = id,
                userId = userFacade.getCurrentUserId()
            )
        )
    }
}
