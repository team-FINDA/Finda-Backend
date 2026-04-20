package finda.findavolunteer.application.port.out.document

import finda.findavolunteer.domain.volunteer.data.VolunteerDocumentData

interface GenerateVolunteerDocumentPort {
    fun generate(data: VolunteerDocumentData): ByteArray
}
