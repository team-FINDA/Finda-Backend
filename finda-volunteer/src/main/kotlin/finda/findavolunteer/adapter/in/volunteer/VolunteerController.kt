package finda.findavolunteer.adapter.`in`.volunteer

import finda.findavolunteer.adapter.`in`.volunteer.dto.request.CreateVolunteerRequest
import finda.findavolunteer.application.port.`in`.volunteer.CreateVolunteerUseCase
import finda.findavolunteer.application.port.`in`.volunteer.DeleteVolunteerUseCase
import finda.findavolunteer.application.port.`in`.volunteer.ExportVolunteerDocumentUseCase
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
import java.net.URLEncoder
import java.util.UUID
import kotlin.text.Charsets.UTF_8

private val DOCX = MediaType.parseMediaType(
    "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
)

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

    @GetMapping("/{volunteerId}/export", produces = ["application/vnd.openxmlformats-officedocument.wordprocessingml.document"])
    fun exportDocument(@PathVariable volunteerId: UUID): ResponseEntity<ByteArray> {
        val bytes = exportVolunteerDocumentUseCase.export(volunteerId)
        val encodedName = URLEncoder.encode("봉사활동확인서_$volunteerId.docx", UTF_8).replace("+", "%20")

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"volunteer.docx\"; filename*=UTF-8''$encodedName")
            .contentType(DOCX)
            .contentLength(bytes.size.toLong())
            .body(bytes)
    }
}
