package finda.findavolunteer.application.service.volunteer

import finda.findavolunteer.application.port.`in`.volunteer.ExportVolunteerDocumentUseCase
import finda.findavolunteer.application.port.out.document.GenerateVolunteerDocumentPort
import finda.findavolunteer.application.port.out.volunteer.LoadVolunteerDocumentPort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ExportVolunteerDocumentService(
    private val loadVolunteerDocumentPort: LoadVolunteerDocumentPort,
    private val generateVolunteerDocumentPort: GenerateVolunteerDocumentPort
) : ExportVolunteerDocumentUseCase {

    override fun export(volunteerId: UUID): ByteArray {
        val documentData = loadVolunteerDocumentPort.loadVolunteerDocumentData(volunteerId)

        return generateVolunteerDocumentPort.generate(documentData)
    }
}
