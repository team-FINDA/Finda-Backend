package finda.findavolunteer.application.port.`in`.volunteer

import java.util.UUID

interface ExportVolunteerDocumentUseCase {
    fun export(volunteerId: UUID): ByteArray
}
