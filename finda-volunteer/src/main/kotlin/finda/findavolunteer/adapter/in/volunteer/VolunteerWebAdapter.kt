package finda.findavolunteer.adapter.`in`.volunteer

import finda.findavolunteer.adapter.`in`.volunteer.dto.request.CreateVolunteerRequest
import finda.findavolunteer.adapter.`in`.volunteer.dto.response.VolunteerDetailResponse
import finda.findavolunteer.adapter.`in`.volunteer.dto.response.VolunteerListResponse
import finda.findavolunteer.adapter.`in`.volunteer.mapper.toCommand
import finda.findavolunteer.adapter.`in`.volunteer.mapper.toResponse
import finda.findavolunteer.application.port.`in`.volunteer.CreateVolunteerUseCase
import finda.findavolunteer.application.port.`in`.volunteer.DeleteVolunteerUseCase
import finda.findavolunteer.application.port.`in`.volunteer.ExportVolunteerDocumentUseCase
import finda.findavolunteer.application.port.`in`.volunteer.VolunteerDetailUseCase
import finda.findavolunteer.application.port.`in`.volunteer.VolunteerListUseCase
import finda.findavolunteer.domain.volunteer.enum.VolunteerStatus
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ContentDisposition
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
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

@Tag(name = "봉사활동", description = "봉사활동 생성, 조회, 삭제, 문서 출력 API")
@RestController
@RequestMapping("/volunteers")
class VolunteerWebAdapter(
    val createVolunteerUseCase: CreateVolunteerUseCase,
    val deleteVolunteerUseCase: DeleteVolunteerUseCase,
    val volunteerDetailUseCase: VolunteerDetailUseCase,
    val volunteerListUseCase: VolunteerListUseCase,
    val exportVolunteerDocumentUseCase: ExportVolunteerDocumentUseCase
) {
    @Operation(summary = "봉사활동 생성", description = "새 봉사활동을 등록합니다.")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    fun createVolunteer(@RequestBody request: CreateVolunteerRequest) =
        createVolunteerUseCase.execute(request.toCommand())

    @Operation(summary = "봉사활동 상세 조회", description = "봉사활동 ID로 상세 정보를 조회합니다.")
    @GetMapping("/{volunteerId}")
    @ResponseStatus(value = HttpStatus.OK)
    fun getVolunteer(@PathVariable volunteerId: UUID): VolunteerDetailResponse =
        volunteerDetailUseCase.execute(volunteerId).toResponse()

    @Operation(summary = "봉사활동 목록 조회", description = "상태(status), 연도(year), 정렬 기준(sortBy)으로 봉사활동 목록을 조회합니다.")
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
    ).map { it.toResponse() }

    @Operation(summary = "봉사활동 삭제", description = "봉사활동 ID로 봉사활동을 삭제합니다.")
    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun deleteVolunteer(@RequestParam volunteerId: UUID) = deleteVolunteerUseCase.execute(volunteerId)

    @Operation(summary = "봉사활동 확인서 출력", description = "봉사활동 확인서를 PDF로 출력합니다.")
    @GetMapping("/{volunteerId}/export", produces = [MediaType.APPLICATION_PDF_VALUE])
    fun exportDocument(
        @PathVariable volunteerId: UUID
    ): ResponseEntity<ByteArray> {
        val bytes = exportVolunteerDocumentUseCase.export(volunteerId)
        val filename = "학교교육계획에 의한 단체봉사활동 실시 확인서(활동내용).pdf"

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_PDF)
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                ContentDisposition.attachment()
                    .filename(filename, Charsets.UTF_8)
                    .build()
                    .toString()
            )
            .contentLength(bytes.size.toLong())
            .body(bytes)
    }
}
