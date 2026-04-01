package finda.findavolunteer.adapter.`in`.volunteer

import finda.findavolunteer.adapter.`in`.volunteer.dto.request.CreateVolunteerRequest
import finda.findavolunteer.application.port.`in`.volunteer.CreateVolunteerUseCase
import finda.findavolunteer.application.port.`in`.volunteer.DeleteVolunteerUseCase
import finda.findavolunteer.application.port.`in`.volunteer.ExportVolunteerDocumentUseCase
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

@RestController
@RequestMapping("/volunteers")
class VolunteerController(
    private val createVolunteerUseCase: CreateVolunteerUseCase,
    private val deleteVolunteerUseCase: DeleteVolunteerUseCase,
    private val exportVolunteerDocumentUseCase: ExportVolunteerDocumentUseCase
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createVolunteer(@RequestBody request: CreateVolunteerRequest) =
        createVolunteerUseCase.execute(request)

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteVolunteer(@RequestParam volunteerId: UUID) =
        deleteVolunteerUseCase.execute(volunteerId)

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
